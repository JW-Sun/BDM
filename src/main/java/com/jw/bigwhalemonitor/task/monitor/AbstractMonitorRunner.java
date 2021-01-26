package com.jw.bigwhalemonitor.task.monitor;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.HttpYarnApp;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import com.jw.bigwhalemonitor.service.SchedulingService;
import com.jw.bigwhalemonitor.service.YarnAppService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.AbstractNoticeableTask;
import com.jw.bigwhalemonitor.task.cmd.CmdRecordRunner;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import com.jw.bigwhalemonitor.util.SpringContextUtils;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


public abstract class AbstractMonitorRunner extends AbstractNoticeableTask implements InterruptableJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMonitorRunner.class);

    protected Scheduling scheduling;
    protected Script script;
    protected Cluster cluster;
    private Thread thread;
    private volatile boolean interrupted = false;

    @Autowired
    private SchedulingService schedulingService;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private YarnAppService yarnAppService;
    @Autowired
    private CmdRecordService cmdRecordService;

    @Override
    public void interrupt() {
        if (!interrupted) {
            interrupted = true;
            thread.interrupt();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        thread = Thread.currentThread();
        String monitorInfoId = jobExecutionContext.getJobDetail().getKey().getName();
        scheduling = schedulingService.getById(monitorInfoId);
        if (scheduling == null) {
            return;
        }
        scheduling.setLastExecuteTime(new Date());
        schedulingService.save(scheduling);
        script = scriptService.getById(scheduling.getScriptIds());
        cluster = clusterService.getById(script.getClusterId());
        executeJob();
    }

    /**
     * 任务逻辑
     */
    public abstract void executeJob();

    protected YarnApp getYarnAppFromDatabase() {
        List<YarnApp> yarnApps = yarnAppService.getByScriptId(scheduling.getScriptIds());
        if (!CollectionUtils.isEmpty(yarnApps)) {
            return yarnApps.get(0);
        }
        return null;
    }

    protected YarnApp getYarnAppFromYarnServer() {
        HttpYarnApp httpYarnApp = YarnApiUtils.getActiveApp(cluster.getYarnUrl(), script.getUser(), script.getQueue(), script.getApp(), 3);
        if (httpYarnApp != null) {
            YarnApp yarnApp = new YarnApp();
            BeanUtils.copyProperties(httpYarnApp, yarnApp);
            yarnApp.setId(null);
            yarnApp.setUid(script.getUid());
            yarnApp.setScriptId(script.getId());
            yarnApp.setClusterId(script.getClusterId());
            yarnApp.setUpdateTime(new Date());
            yarnApp.setAppId(httpYarnApp.getId());
            yarnApp.setStartedTime(new Date(httpYarnApp.getStartedTime()));
            return yarnApp;
        }
        return null;
    }

    // 获取最后一次提交未运行的应用的状态
    protected HttpYarnApp getLastNoActiveYarnApp() {
        return YarnApiUtils.getLastNoActiveApp(cluster.getYarnUrl(), script.getUser(), script.getQueue(), script.getApp(), 3);
    }

    /**
     * 重启
     * @return
     */
    protected boolean restart() {
        Script script = scriptService.getById(scheduling.getScriptIds());
        // 检查是否存在当前脚本 [ 未执行 ]或 [ 正在执行 ] 的任务
        CmdRecordWithBLOBs cmdRecord = cmdRecordService.getByQuery4(scheduling.getScriptIds(), Constant.EXEC_STATUS_UNSTART, Constant.EXEC_STATUS_DOING);
        if (cmdRecord != null) {
            return true;
        }
        CmdRecordWithBLOBs record = new CmdRecordWithBLOBs();
        record.setUid(scheduling.getUid());
        record.setScriptId(script.getId());
        record.setStatus(Constant.EXEC_STATUS_UNSTART);
        record.setAgentId(script.getAgentId());
        record.setClusterId(script.getClusterId());
        record.setSchedulingId(scheduling.getId());
        record.setContent(script.getScript());
        record.setTimeout(script.getTimeout());
        record.setCreateTime(new Date());
        record = cmdRecordService.save(record);
        try {
            CmdRecordRunner.build(record);
        } catch (SchedulerException e) {
            LOGGER.error("schedule submit error", e);
            return false;
        }
        return true;
    }

    public static void build(Scheduling scheduling) throws SchedulerException {
        Script script = SpringContextUtils.getBean(ScriptService.class).getById(scheduling.getScriptIds());
        if (script.getType() == Constant.SCRIPT_TYPE_SPARK_STREAMING) {
            SchedulerUtils.schedulerCronJob(SparkMonitorRunner.class,
                    scheduling.getId(),
                    Constant.JobGroup.MONITOR,
                    scheduling.generateCron(),
                    scheduling.getStartTime(),
                    scheduling.getEndTime(),
                    null);
        } else if (script.getType() == Constant.SCRIPT_TYPE_FLINK_STREAMING) {
            SchedulerUtils.schedulerCronJob(FlinkMonitorRunner.class,
                    scheduling.getId(),
                    Constant.JobGroup.MONITOR,
                    scheduling.generateCron(),
                    scheduling.getStartTime(),
                    scheduling.getEndTime(),
                    null);
        }
    }
}
