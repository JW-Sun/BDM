package com.jw.bigwhalemonitor.task.cmd;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.config.SshConfig;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.AbstractCmdRecordTask;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@DisallowConcurrentExecution
public class CmdRecordRunner extends AbstractCmdRecordTask implements InterruptableJob {

    private static final Pattern PATTERN = Pattern.compile("application_\\d+_\\d+");
    private static final Pattern PATTERN1 = Pattern.compile("time mark: (\\d+)");
    private static final Logger LOGGER = LoggerFactory.getLogger(CmdRecordRunner.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private Thread thread;
    // volatile语义使得数据的可见及时性，及时将数据从工作内存刷到主内存以及数据从主内存刷到工作内存中
    private volatile boolean commandFinish = false;
    private volatile boolean interrupted = false;
    private CmdRecordWithBLOBs cmdRecord;
    private Script script;
    private String yarnUrl;
    private Connection conn;

    @Autowired
    private ScriptService scriptService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private SshConfig sshConfig;

    @Override
    public void interrupt() {
        if (!commandFinish && !interrupted) {
            // 击杀任务，通过grep具体的任务queue和app的进行grep
            kill();
            interrupted = true;
            thread.interrupt();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 当前线程
        thread = Thread.currentThread();
        // quartz任务的key，也就是cmdRecord的Id
        String cmdRecordId = jobExecutionContext.getJobDetail().getKey().getName();
        // 查到cmdRecord
        cmdRecord = cmdRecordService.getById(cmdRecordId);
        // 赋值默认的超时时间5s
        if (cmdRecord.getTimeout() == null) {
            cmdRecord.setTimeout(5);
        }
        // 计算出超时的时间
        Date timeoutTime = DateUtils.addMinutes(cmdRecord.getCreateTime(), cmdRecord.getTimeout());
        Date current = new Date();
        //超时的情况统一由CmdTimeoutJob处理
        if (current.after(timeoutTime)) {
            return;
        }
        //更新cmdRecord正在执行
        String now = dateFormat.format(current);
        try {
            cmdRecord.setStartTime(dateFormat.parse(now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 改变状态为运行中，并进行保存
        cmdRecord.setStatus(Constant.EXEC_STATUS_DOING);
        cmdRecordService.save(cmdRecord);
        // 通过命令获得任务的脚本信息
        script = scriptService.getById(cmdRecord.getScriptId());
        // 通过cmd命令获得scheduling的调度信息
        Scheduling scheduling = StringUtils.isNotBlank(cmdRecord.getSchedulingId()) ? schedulingService.getById(cmdRecord.getSchedulingId()) : null;
        Session session = null;
        try {
            String instance;
            // 如果是spark|flink的批任务或是流任务, clusterId获得代理的实例
            if (script.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
                yarnUrl = clusterService.getById(cmdRecord.getClusterId()).getYarnUrl();
                instance = agentService.getInstanceByClusterId(cmdRecord.getClusterId(), true);
                cmdRecord.setAgentInstance(instance);
            } else {
                instance = agentService.getInstanceByAgentId(cmdRecord.getAgentId(), true);
                cmdRecord.setAgentInstance(instance);
            }
            // 服务器实例的地址是否是分开的
            if (instance.contains(":")) {
                String [] arr = instance.split(":");
                conn = new Connection(arr[0], Integer.parseInt(arr[1]));
            } else {
                conn = new Connection(instance);
            }
            conn.connect(null, sshConfig.getConnectTimeout(), 30000);
            conn.authenticateWithPassword(sshConfig.getUser(), sshConfig.getPassword());
            int ret;
            session = conn.openSession();
            String command = cmdRecord.getContent();
            if (cmdRecord.getArgs() != null) {
                JSONObject argsObj = JSON.parseObject(cmdRecord.getArgs());
                for (Map.Entry<String, Object> entry : argsObj.entrySet()) {
                    command = command.replace(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            //为确保调度流程的准确性，应用名称添加实例ID
            if (scheduling != null) {
                if (script.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH) {
                    command = command.replace("--name " + script.getApp(), "--name " + script.getApp() + "_instance" + now);
                } else if (script.getType() == Constant.SCRIPT_TYPE_FLINK_BATCH) {
                    command = command.replace("-ynm " + script.getApp(), "-ynm " + script.getApp() + "_instance" + now);
                }
            }
            session.execCommand("echo time mark: $(date +%s) && " + command);
            if (!interrupted) {
                //并发执行读取
                readOutput(session);
            }
            //主逻辑执行结束，就不再需要执行interrupt()
            commandFinish = true;
            if (!interrupted) {
                //等待指令执行完退出
                session.waitForCondition(ChannelCondition.EXIT_STATUS, 1000);
                //取得指令执行结束后的状态
                ret = session.getExitStatus();
                if (ret == 0) {
                    cmdRecord.setStatus(Constant.EXEC_STATUS_FINISH);
                    if (script.getType() == Constant.SCRIPT_TYPE_SHELL_BATCH) {
                        //Shell脚本提交子任务(定时任务)
                        submitNextCmdRecord2(cmdRecord, scheduling, scriptService);
                    } else {
                        //设置yarn任务状态
                        if (script.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH || script.getType() == Constant.SCRIPT_TYPE_FLINK_BATCH) {
                            cmdRecord.setJobFinalStatus("UNDEFINED");
                        }
                        if (cmdRecord.getJobId() == null) {
                            LOGGER.warn("脚本：" + script.getName() + "，未能读取到Yarn应用ID！为确保告警的准确性，请将提交Yarn任务的日志级别设置为: INFO");
                        }
                    }
                } else {
                    cmdRecord.setStatus(Constant.EXEC_STATUS_FAIL);
                    //处理失败(定时任务)
                    notice(cmdRecord, scheduling, null, Constant.ERROR_TYPE_FAILED);
                }
                cmdRecord.setFinishTime(new Date());
            } else {
                CmdRecord recordForTimeout = cmdRecordService.getById(cmdRecordId);
                cmdRecord.setStatus(recordForTimeout.getStatus());
            }
            cmdRecordService.save(cmdRecord);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            if (!interrupted) {
                cmdRecord.setStatus(Constant.EXEC_STATUS_FAIL);
                notice(cmdRecord, scheduling, null, Constant.ERROR_TYPE_FAILED);
            } else {
                CmdRecord recordForTimeout = cmdRecordService.getById(cmdRecordId);
                cmdRecord.setStatus(recordForTimeout.getStatus());
            }
            cmdRecord.setErrors(e.getMessage());
            cmdRecordService.save(cmdRecord);
        } finally {
            if (session != null) {
                session.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void readOutput(Session session) {
        // 输出给定核心线程数与最大线程数相同为2的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            // 确确保线程池中的任务都完成了再进行西面的操作
            CountDownLatch countDownLatch = new CountDownLatch(2);
            final InputStream stdOut = new StreamGobbler(session.getStdout());
            final InputStream stdErr = new StreamGobbler(session.getStderr());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        readContent(true, stdOut);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        readContent(false, stdErr);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
        } catch (InterruptedException e) {

        } finally {
            executorService.shutdownNow();
        }
    }

    /***
     *  输出
     * @param stdout
     * @param in
     */
    private void readContent(boolean stdout, InputStream in) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                if (out.size() < 61440) {
                    out.write(buffer, 0, length);
                    String tmp = out.toString("UTF-8");
                    if (stdout) {
                        cmdRecord.setOutputs(tmp);
                    } else {
                        cmdRecord.setErrors(tmp);
                    }
                    cmdRecordService.save(cmdRecord);
                }
            }
            if (out.size() > 0) {
                String content = out.toString("UTF-8");
                if (stdout) {
                    cmdRecord.setOutputs(content);
                } else {
                    cmdRecord.setErrors(content);
                }
                if (yarnUrl != null) {
                    this.extraInfo(yarnUrl, content);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 提取yarn信息
     * @param yarnUrl
     * @param message
     * @return
     */
    private void extraInfo(String yarnUrl, String message) {
        if (cmdRecord.getJobId() == null) {
            Matcher matcher = PATTERN.matcher(message);
            if (matcher.find()) {
                String id = matcher.group();
                cmdRecord.setJobId(id);
                cmdRecord.setUrl(yarnUrl + "/proxy/" + id + "/");
            }
        }
    }

    // kill task
    private void kill() {
        Matcher matcher = PATTERN1.matcher(cmdRecord.getOutputs());
        if (!matcher.find()) {
            LOGGER.error("未能匹配时间标记，不能执行kill命令");
        }
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
        // 从离线调度节点中的输出部分获得 time mark的时间戳 单位ms
        long timestamp = Long.parseLong(matcher.group(1) + "000");
        // 对于时间戳的申请范围是+-1s的范围
        String lstartM1 = dateFormat.format(new Date(timestamp - 1000));
        String lstart = dateFormat.format(new Date(timestamp));
        String lstartA1 = dateFormat.format(new Date(timestamp + 1000));
        String cmd;
        String commandTemplate;
        if (script.getType() == Constant.SCRIPT_TYPE_SHELL_BATCH) {
            cmd = cmdRecord.getContent().replaceAll("/\\*", "/\\\\*");
            commandTemplate = "kill -9 $(ps -eo pid,lstart,cmd | grep '%s %s' | grep -v 'grep' | grep -v 'echo time mark' | awk '{print $1}')";
        } else {
            // 着重看spark与flink的流批任务处理
            if (script.getType() == Constant.SCRIPT_TYPE_SPARK_STREAMING || script.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH) {
                // app和queue的前后位置拼接在cmd中，间隔符就是 '.*'
                if (script.getScript().indexOf("--queue " + script.getQueue()) > script.getScript().indexOf("--name " + script.getApp())) {
                    cmd = script.getApp() + ".*" + script.getQueue();
                } else {
                    cmd = script.getQueue() + ".*" + script.getApp();
                }
            } else {
                // 对应flink任务脚本的队列名称以及任务名称
                if (script.getScript().indexOf("-yqu " + script.getQueue()) > script.getScript().indexOf("-ynm " + script.getApp())) {
                    cmd = script.getApp() + ".*" + script.getQueue();
                } else {
                    cmd = script.getQueue() + ".*" + script.getApp();
                }
            }
            // 拼接成kill任务的命令
            // 命令的内容就是kill掉一些pid
            // 这个pid的得到是靠匹配‘%s.*%s’, 非grep，非echo time mark获得，最后获得第一列的内容，就是pid
            commandTemplate = "kill -9 $(ps -eo pid,lstart,cmd | grep '%s.*%s' | grep -v 'grep' | grep -v 'echo time mark' | awk '{print $1}')";
        }
        String command = String.format(commandTemplate, lstartM1, cmd) + " ; " +
                String.format(commandTemplate, lstart, cmd) + " ; " +
                String.format(commandTemplate, lstartA1, cmd);
        Session session = null;
        try {
            session = conn.openSession();
            session.execCommand(command);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void buildBlob(CmdRecordWithBLOBs cmdRecordWithBLOBs) throws SchedulerException {
        SchedulerUtils.schedulerSimpleJob(CmdRecordRunner.class, cmdRecordWithBLOBs.getId(), Constant.JobGroup.CMD, 0, 0);
    }
    public static void build(CmdRecordWithBLOBs cmdRecord) throws SchedulerException {
        SchedulerUtils.schedulerSimpleJob(CmdRecordRunner.class, cmdRecord.getId(), Constant.JobGroup.CMD, 0, 0);
    }

}
