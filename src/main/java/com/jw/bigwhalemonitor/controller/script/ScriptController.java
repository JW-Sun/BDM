package com.jw.bigwhalemonitor.controller.script;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.config.SshConfig;
import com.jw.bigwhalemonitor.config.YarnConfig;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.SchedulerService;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/script")
public class ScriptController extends BaseController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ClusterUserService clusterUserService;

    @Autowired
    private SshConfig sshConfig;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private YarnConfig yarnConfig;

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoScript dtoScript) {
        LoginUser currentUser = getCurrentUser();
        if (!currentUser.isRoot()) {
            dtoScript.setUid(currentUser.getId());
        }

        return null;
    }

    @GetMapping("/getall.api")
    public Msg getAll() {
        // 从当前登录的用户入手
        LoginUser currentUser = getCurrentUser();
        List<Script> scripts;
        // 该root是否有root执行权限
        if (currentUser.isRoot()) {
            scripts = scriptService.getByUid(currentUser.getId());
        } else {
            scripts = scriptService.getAll();
        }
        List<DtoScript> dtoScriptList = new ArrayList<>();
        for (Script script : scripts) {
            DtoScript dtoScript = new DtoScript();
            BeanUtils.copyProperties(script, dtoScript);
            dtoScriptList.add(dtoScript);
        }
        return success(dtoScriptList);
    }


    // 保存脚本-基础就是之前设置好的集群、用户、代理、以及计算的框架，进行执行脚本的添添加
    @PostMapping("/save.api")
    public Msg saveOrUpdate(@RequestBody DtoScript dtoScript) {
        // 检验脚本对象的合法性 -- 从执行脚本中获得 name queue的信息。
        String validate = dtoScript.validate();
        if (validate != null) {
            return failed(validate);
        }
        // 获取用户
        LoginUser currentUser = getCurrentUser();
        if (!currentUser.isRoot()) {
            dtoScript.setUid(currentUser.getId());
        }

        /* 检查脚本内容的合法 */
        String rs = checkScriptIsLegal(dtoScript);
        if (rs != null) {
            return failed(rs);
        }

        /* 检查脚本任务的资源管理请求 */
        Integer type = dtoScript.getType();
        if (type != Constant.SCRIPT_TYPE_SHELL_BATCH) {
            if (yarnConfig.getAppMemoryThreshold() > 0 && !yarnConfig.getAppWhiteList().contains(dtoScript.getAgentId())) {
                try {
                    // 计算总的内存参数
                    int totalMemory = calResource(type, dtoScript.getScript()).get("totalMemory");
                    if (totalMemory > yarnConfig.getAppMemoryThreshold()) {
                        return failed("总共的内存资源超过了占用哦，最大限制数值在：" + yarnConfig.getAppMemoryThreshold() + " M. ");
                    }
                } catch (Exception e) {
                    return failed("计算总内存参数发生错误。");
                }
            }
            /* 补充必要的任务参数 */
            appendExtraParamters(dtoScript);
        }

        /* 处理脚本任务是否在运行（运行就停止）以及操作已经存在的脚本任务中的正在进行的调度功能 */
        Date date = new Date();
        if (dtoScript.getId() == null) {
            dtoScript.setCreateTime(date);
        } else {
            Script scriptById = scriptService.getById(dtoScript.getId());
            // 如果查不到就直接报错
            if (scriptById == null) {
                return failed("要更新的脚本任务是不存在的");
            }

            // 如果脚本的任务的‘类型是否是batch的离线任务’ 则需要在schedule中查询是否存在任务调度
            if (scriptById.isOffline() != dtoScript.isOffline()) {
                // 需要在调度中进行查询
                List<Scheduling> scheduleByScriptId = schedulerService.getByScriptId(dtoScript.getId());
                if (scheduleByScriptId != null && scheduleByScriptId.size() > 0) {
                    return failed("更新的脚本任务还存在着调度的任务。");
                }
            }

            if (scriptById.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
                // 如果更改的脚本任务，改变了脚本类型、集群、或者队列的时候需要
                if (checkYarnAppAliveIfChangeClusterOrQueue(scriptById, dtoScript)) {
                    // 如果对应的原来的脚本任务还在运行
                    return failed("修改的脚本任务还在运行中。");
                }

                // 判断执行任务的jar包是否发生变化
                if (dtoScript.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
                    String preJarPath = scriptService.getJarsPath(scriptById.getScript());
                    String nowJarPath = scriptService.getJarsPath(scriptById.getScript());

                    /* 任务jar的路径不同，需要将保存在hdfs中的jar包资源进行校验以及删除 */
                    if (!preJarPath.equals(nowJarPath)) {
                        scriptService.deleteJar(scriptById);
                    }
                } else {
                    scriptService.deleteJar(scriptById);
                }
            }
        }

        dtoScript.setUpdateTime(date);
        Script script = new Script();
        BeanUtils.copyProperties(dtoScript, script);
        if (!StringUtils.isBlank(dtoScript.getUser())) {
            script.setUser(sshConfig.getUser());
        }
        scriptService.save(script);
        return success(script);
    }

    /***
     *
     * @param scriptById 更新前的原来的任务脚本
     * @param dtoScript 请求体传过来的 更新后的任务脚本
     * @return
     */
    private boolean checkYarnAppAliveIfChangeClusterOrQueue(Script scriptById, DtoScript dtoScript) {
        // 获得原来的脚本队列
        String queue = scriptById.getQueue();
        if (dtoScript.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
            // 脚本任务中集群或者队列发生变化的时候
            // 需要从原来的脚本任务中找到旧的集群并且
            if (!scriptById.getClusterId().equals(dtoScript.getScript()) || !scriptById.getQueue().equals(dtoScript.getQueue())) {
                Cluster oldCluster = clusterService.getById(scriptById.getId());
                // 旧脚本以及旧集群是否还存在正在执行的任务
                boolean hasRunning = YarnApiUtils.getActiveApp(oldCluster.getYarnUrl(), scriptById.getUser(), queue, scriptById.getApp(), 4) != null;
                return hasRunning;
            }
            return false;
        } else {
            Cluster oldCluster = clusterService.getById(scriptById.getId());
            // 旧脚本以及旧集群是否还存在正在执行的任务
            boolean hasRunning = YarnApiUtils.getActiveApp(oldCluster.getYarnUrl(), scriptById.getUser(), queue, scriptById.getApp(), 4) != null;
            return hasRunning;
        }
    }


    /***
     *  获得user以及script信息
     * @param dtoScript
     */
    private void appendExtraParamters(DtoScript dtoScript) {
        // 获得用户以及脚本的信息
        String user = dtoScript.getUser();
        String script = dtoScript.getScript();
        if (dtoScript.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH || dtoScript.getType() == Constant.SCRIPT_TYPE_SPARK_STREAMING) {
            // 这个参数的作用是让spark-submit的任务提交了之后可以直接停止会话窗口，交给后台执行
            if (!script.contains("spark.yarn.submit.waitAppCompletion")) {
                int pos = script.indexOf("--");
                String left = script.substring(0, pos);
                String right = script.substring(pos);
                dtoScript.setScript(left + " --conf spark.yarn.submit.waitAppCompletion=false " + right);
            }
            // 任务参数中的proxy-user以 dtoScript的user为标准
            if (!script.contains("proxy-user")) {
                int pos = script.indexOf("--");
                String s = script.substring(0, pos) + " --proxy-user " + user + " " + script.substring(pos);
                dtoScript.setScript(s);
            } else {
                script.replaceAll("--proxy-user [\\w-.,]+", "--proxy-user " + user);
            }
        } else if (dtoScript.getType() == Constant.SCRIPT_TYPE_FLINK_BATCH || dtoScript.getType() == Constant.SCRIPT_TYPE_FLINK_STREAMING) {
            // -d 表示分断式的任务执行
            if (!script.contains("-d")) {
                int pod = script.indexOf("-");
                String post = script.substring(0, pod) + " -d " + script.substring(pod);
                dtoScript.setScript(post);
            }
            // 如果这个代理用户存在则需要加到对应的参数中
            if (StringUtils.isNotBlank(user)) {
                if (!script.contains("-yD ypu=")) {
                    int pos = script.indexOf("-");
                    String post = script.substring(0, pos) + " -yD ypu= " + user + " " + script.substring(pos);
                    dtoScript.setScript(post);
                } else {
                    script = script.replaceAll("-yD ypu=[\\w-.,]+", "-yD ypu=" + user);
                    dtoScript.setScript(script);
                }
            }
        }
    }

    // 脚本任务的参数分配
    private Map<String, Integer> calResource(int type, String script) {
        Map<String, Integer> result = new HashMap<>();

        // 对spark任务的参数进行设置
        Map<String, String> spark = new HashMap<>();
        spark.put("--driver-memory", "512M");
        spark.put("--driver-cores", "1");
        spark.put("--executor-memory", "1024M");
        spark.put("--num-executors", "1");
        spark.put("--executor-cores", "1");
        spark.put("spark.yarn.executor.memoryOverhead", "-");

        // 对flink任务进行参数设置
        Map<String, String> flink = new HashMap<>();
        flink.put("-yjm", "512M");
        flink.put("-ytm", "1024M");
        flink.put("-yn", "1");
        flink.put("-ys", "1");

        // 将脚本中的参数都切分开来，针对不同指标进行解决
        String[] tokens = script.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (type == Constant.SCRIPT_TYPE_SPARK_STREAMING || type == Constant.SCRIPT_TYPE_SPARK_BATCH) {
                // 在executor执行的时候，使用的内存可能会超过executor-memory，因此需要为其额外预留一部分资源
                if (token.startsWith("spark.yarn.executor.memoryOverhead")) {
                    spark.put("spark.yarn.executor.memoryOverhead", token.split("=")[1]);
                } else if (spark.containsKey(token)) {
                    // 因为切分之后的结果是 --yjm 1024m 获得实际上的参数需要将下标往后移动一位
                    spark.put(token, tokens[i + 1]);
                }
            } else {
                if (flink.containsKey(token)) {
                    flink.put(token, tokens[i + 1]);
                }
            }
        }

        // 如果是spark 流计算类型或者是spark批处理类型
        if (type == Constant.SCRIPT_TYPE_SPARK_STREAMING || type == Constant.SCRIPT_TYPE_SPARK_BATCH) {
            String driverMemoryStr = spark.get("--driver-memory").toUpperCase();
            // driver 的内存
            int driverMemory = parseMemory(driverMemoryStr);
            String executorMemoryStr = spark.get("--executor-memory");
            // executor 的内存
            int executorMemory = parseMemory(executorMemoryStr);
            int numExecutors = Integer.parseInt(spark.get("--num-executors"));
            String memoryOverheadStr = spark.get("spark.yarn.executor.memoryOverhead");
            if (!"-".equals(memoryOverheadStr)) {
                int memoryOverhead = parseMemory(memoryOverheadStr);
                result.put("totalMemory", numExecutors * (executorMemory + memoryOverhead) + (driverMemory + memoryOverhead));
            } else {
                result.put("totalMemory", (int)(numExecutors * (executorMemory + Math.max(executorMemory * 0.1, 384)) + (driverMemory + Math.max(driverMemory * 0.1, 384))));
            }
            // 核心数量
            int driverCores = Integer.parseInt(spark.get("--driver-cores"));
            int executorCores = Integer.parseInt(spark.get("--executor-cores"));
            result.put("totalCores", numExecutors * executorCores + driverCores);
        } else {
            // jobmanager内存大小
            String yjmStr = flink.get("-yjm").toUpperCase();
            int yjm = parseMemory(yjmStr);

            // taskManager内存大小
            String ytmStr = flink.get("-ytm").toUpperCase();
            int ytm = parseMemory(ytmStr);

            // taskManager 的数量
            int yn = Integer.parseInt(flink.get("-yn"));
            result.put("totalMemory", yn * ytm + yjm);

            // slot的数量
            int ys = Integer.parseInt(flink.get("-ys"));
            result.put("totalCores", yn * ys + 1);
        }
        return result;
    }

    /***
     *  解析内存的大小
     * @param s
     * @return
     */
    private int parseMemory(String s) {
        s = s.toUpperCase();
        int val;
        if (s.contains("M")) {
            val = Integer.parseInt(s.substring(0, s.indexOf("M")));
        } else if (s.contains("G")) {
            val = Integer.parseInt(s.substring(0, s.indexOf("G")));
        } else {
            val = Integer.parseInt(s);
        }
        return val;
    }


    /***
     *  检查脚本的合法性
     *   主要是分为新增和更新两个部分
     *   主要校验的地方是 [user-cluster-app] 这三者形成的任务组合标记。
     * @param dtoScript
     * @return
     */
    private String checkScriptIsLegal(DtoScript dtoScript) {
        // 新增脚本
        if (dtoScript.getId() == null) {
            // 对于新增加的脚本，需要进行校验的有 脚本任务相关的之前就设置好了的
            // 脚本的名称必须是唯一的
            List<Script> byName = scriptService.getByName(dtoScript.getName());
            if (!byName.isEmpty()) {
                return "脚本名称重复";
            }

            /* 验证该clusterId下的代理们有没有可以使用的实例 方法链比较长 */
            if (dtoScript.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
                // 通过集群的Id来获取对应的实例---检查agent的实例是否可以登录成功
                try {
                    agentService.getInstanceByClusterId(dtoScript.getClusterId(), false);
                } catch (Exception e) {
                    return "选择的集群之下没有可用的代理实例";
                }

                // 确定完成存在可以使用的代理实例的时候
                // 检查yarn应用的名称是否是重复的
                Set<String> queueAndApps = new HashSet<>();
                List<Script> scriptByClusterId = scriptService.getByClusterId(dtoScript.getClusterId());
                // 检查该集群的已经存在的脚本中的 user+queue+app 的名称
                for (Script script : scriptByClusterId) {
                    queueAndApps.add(script.getUser() + "$" + script.getQueue() + "$" + script.getApp());
                }

                /* 将脚本中的队列信息依据CLusterUser为标准进行校验与替换 */
                dealQueueAndUser(queueAndApps, dtoScript);

                // 为当前脚本设定【user+queue+app】组合的标识，并判断这个组合是否已经在属于该集群的脚本中出现过
                String qaa = dtoScript.getUser() == null ? sshConfig.getUser() : dtoScript.getUser()
                        + "$" + dtoScript.getQueue() + "$" + dtoScript.getApp();
                for (String queueAndApp : queueAndApps) {
                    if (qaa.equals(queueAndApp)) {
                        return "该集群下，任务脚本的标识【user+cluster+app】 " + qaa + " 存在重复！";
                    }
                }
            } else {
                try {
                    agentService.getInstanceByClusterId(dtoScript.getClusterId(), false);
                } catch (Exception e) {
                    return "shell batch 的模式的情况下，没有可以使用的实例。";
                }
            }
        } else {
            /* 更新脚本时的操作 */
            Script script = scriptService.getById(dtoScript.getId());
            if (script == null) {
                return "脚本不知道是在做什么。";
            }
            // 脚本的名称的要求是唯一的，如果发生变更需要判断name是否冲突
            if (!dtoScript.getName().equals(script.getName())) {
                List<Script> scriptByName = scriptService.getByName(dtoScript.getName());
                for (Script script1 : scriptByName) {
                    // 如果name已经存在并且是其他的，那么就会爆重复
                    if (!script1.getId().equals(dtoScript.getId())) {
                        return "更新的脚本是重复的哦";
                    }
                }
            }

            // 先判断该脚本任务中的代理是否存在可以运行的实例
            if (!dtoScript.getType().equals(Constant.SCRIPT_TYPE_SHELL_BATCH)) {
                try {
                    agentService.getInstanceByClusterId(dtoScript.getClusterId(), false);
                } catch (Exception e) {
                    return "该集群下没有可以使用的代理实例。";
                }
                // 校验是否存在相同的yarn任务 【user+cluster+appname】
                Set<String> queueAndApps = new HashSet<>();
                List<Script> scripts = new ArrayList<>();
                // 将该集群下除了这个script的其余脚本加入到列表中
                List<Script> scriptByClusterId = scriptService.getByClusterId(dtoScript.getClusterId());
                for (Script script1 : scriptByClusterId) {
                    if (!script1.getId().equals(dtoScript.getId())) {
                        scripts.add(script1);
                    }
                }

                // 将该集群下的除了要被更新的yarn脚本任务的名称提取出来
                for (Script sp : scripts) {
                    String yarnName = sp.getUser() + "$" + sp.getClusterId() + "$" + sp.getApp();
                    queueAndApps.add(yarnName);
                }

                /* 处理脚本的任务队列 */
                dealQueueAndUser(queueAndApps, dtoScript);

                // 当前任务脚本的名称
                String queueAndApp = (dtoScript.getUser() == null ? sshConfig.getUser() : dtoScript.getUser()) + "$" + dtoScript.getQueue() + "$" + dtoScript.getApp();

                // 检查这个脚本任务是否已经在 同用户同集群 下存在
                for (String andApp : queueAndApps) {
                    if (andApp.equals(queueAndApp)) {
                        return "Yarn任务名称已经存在";
                    }
                }
            } else {
                // shell batch直接检查代理agent中是否有可用的实例
                try {
                    agentService.getInstanceByClusterId(dtoScript.getClusterId(), false);
                } catch (Exception e) {
                    return "该代理下已经没有可以使用的代理";
                }
            }
        }
        // 什么都不返回那么就说明脚本任务是合法的。
        return null;
    }

    /***
     *  处理用户以及队列
     * @param queueAndApps
     * @param dtoScript
     */
    private void dealQueueAndUser(Set<String> queueAndApps, DtoScript dtoScript) {
        // 将脚本的user设置为null，后序进行补充
        dtoScript.setUser(null);
        // 请求中的脚本
        String script = dtoScript.getScript();
        // 使用uid以及clusterid找到直接对应的ClusterUser,, 这是查询到的其实就是只有一个值
        List<ClusterUser> clusterUserByUidAndClusterId = clusterUserService.getUidAndClusterId(dtoScript.getUid(), dtoScript.getClusterId());

        // 这个用户指定的就是特定uid以及clusterId的配置好的集群用户
        ClusterUser user = clusterUserByUidAndClusterId.get(0);
        String que = user.getQueue();
        String requestQue = dtoScript.getQueue(); // 请求自带的队列

        /*  开始处理任务类型为spark和flink的情况
        *   需要完全以ClusterUser作为标准
        *   */

        /*处理计算类型是Spark的任务*/
        if (dtoScript.getType() == Constant.SCRIPT_TYPE_SPARK_STREAMING || dtoScript.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH) {
            // 之前查到的集群用户对应的user
            String proxyUser = user.getUser();
            // 该值不为空直接就赋值给请求发过来的user,或者使用配置文件中的user
            if (StringUtils.isNotBlank(proxyUser)) {
                dtoScript.setUser(proxyUser);
            } else {
                proxyUser = sshConfig.getUser();
            }

            // 判断处理正确的队列情况.
            String legalQueue;
            // 如果请求过来的指定的队列是有值，则需要判断请求带过来的队列是否合法，参考获取的ClusterUser集群信息
            if (requestQue != null) {
                // ClusterUser中指定的queue是单个值，就直接赋值
                if (!que.contains(",")) {
                    legalQueue = que;
                } else {
                    // 集群用户指定的队列有多个，需要与请求中指定的队列进行判别
                    // 该user该cluster下的合法的队列
                    legalQueue = getLegalQueue(queueAndApps, proxyUser, que, requestQue, dtoScript.getApp());
                }
                // 将原始脚本中的队列信息进行替换, 目的是将指定的队列
                script = script.replaceAll("--queue [\\w-.,]+", "--queue " + legalQueue);
            } else {
                // 请求中就没有指定队列，就将 uid+clusterId为条件查找到的队列进行赋值
                if (!que.contains(",")) {
                    legalQueue = que;
                } else {
                    legalQueue = getLegalQueue(queueAndApps, proxyUser, que, null, dtoScript.getApp());
                }
                // 将queue的信息加入到脚本之中
                // 这是随便的一个配置项
                int pos = script.indexOf("--");
                String head = script.substring(0, pos);
                String tail = script.substring(pos);
                script = head + " --queue " + legalQueue + " " + tail;
            }
            dtoScript.setQueue(legalQueue);
        } else {
            /*Flink计算任务 --- 流程与处理spark任务大致相同*/
            // 获取用户，以及请求体中的集群
            String proxyUser = sshConfig.getUser();
            Cluster clusterById = clusterService.getById(dtoScript.getClusterId());
            if (clusterById.getFlinkProxyUserEnabled() && StringUtils.isNotBlank(user.getUser())) {
                // 这里进行设置的原因就是 clusterUser通过（uid和clusterId）寻找的唯一的指定队列的集群用户
                dtoScript.setUser(user.getUser());
                proxyUser = user.getUser();
            }
            // 开始寻找合法的队列
            String legalQueue;
            if (requestQue != null) { // 请求体中包含队列, 需要在之前设定下的clusterUser进行设置
                if (!que.contains(",")) {
                    legalQueue = que;
                } else {
                    legalQueue = getLegalQueue(queueAndApps, proxyUser, que, requestQue, dtoScript.getApp());
                }
                // 将脚本中的命令中的合法队列值进行替换
                script.replaceAll("-yqu [\\w-.,]+", "-yqu " + legalQueue);
            } else { // 请求体中是不包含队列信息，直接依靠CLusterUser中定义好的进行选择
                if (!que.contains(",")) {
                    legalQueue = que;
                } else {
                    legalQueue = getLegalQueue(queueAndApps, proxyUser, que, null, dtoScript.getApp());
                }
                int pos = script.indexOf("-");
                String head = script.substring(0, pos);
                String tail = script.substring(pos);
                script = head + " -yqu " + legalQueue + " " + tail;
            }
            dtoScript.setQueue(legalQueue);
        }
        dtoScript.setScript(script);
    }

    /***
     *  CLusterUser中指定的队列有多个的时候，需要与请求中的队列进行比较
     *  随机找到一个合法的队列进行返回
     *
     *   随机性的引入可以很好的提升任务的  [[负载均衡的能力]]
     *
     * @param queueAndApps 请求脚本中指定的集群中的 user+queue+app
     * @param proxyUser 集群用户或者配置文件中默认的用户
     * @param que 通过请求体中uid+clusterId获得的集群用户所有的队列的权限
     * @param requestQue  请求体中的队列
     * @param app appname
     * @return
     */
    private String getLegalQueue(Set<String> queueAndApps, String proxyUser, String que, String requestQue, String app) {
        String legalQueue = null;
        // 此时clusterUser中对应了多个队列
        String[] queSplitArr = que.split(",");
        if (requestQue != null) {
            for (String queue : queSplitArr) {
                // 如果有符合条件的队列就直接进行返回
                if (queue.equals(requestQue)) {
                    legalQueue = queue;
                    break;
                }
            }
        }

        // 如果clusteruser中的集群没有与之匹配的，或者本身请求体传过来的对象本身没有指定que
        // 那就使用 CLusterUser提前定义的条件合法的que来进行计算
        if (legalQueue == null) {
            List<String> legalQueues = new ArrayList<>();
            // 遍历ClusterUser中指定好的，前置条件合法的queue
            for (String queue : queSplitArr) {
                String temp = proxyUser + "$" + queue + "$" + app;
                boolean vis = false;
                // 遍历现有 script 中的 [[相同cluster上的相同uid]] 的，如果存在就跳过，直到没有遇到过的组合
                for (String queueAndApp : queueAndApps) {
                    if (queueAndApp.equals(temp)) {
                        vis = true;
                        break;
                    }
                }
                // 如果这个temp没有重复就直接添加进结果集中。但是不退出，将所有符合条件的加入过后，进行随机数的选择
                if (!vis) {
                    legalQueues.add(temp);
                }
            }
            if (legalQueues != null && legalQueues.size() > 0) {
                legalQueue = legalQueues.get(new Random().nextInt(legalQueues.size()));
            }
        }
        return legalQueue;
    }


}



