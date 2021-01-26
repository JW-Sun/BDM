package com.jw.bigwhalemonitor.task.common;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.HttpYarnApp;
import com.jw.bigwhalemonitor.config.YarnConfig;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.service.YarnAppService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.AbstractNoticeableTask;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;



@DisallowConcurrentExecution
public class RefreshActiveStateAppsJob extends AbstractNoticeableTask implements InterruptableJob {

    private static final Pattern PATTERN = Pattern.compile("_instance\\d+$");

    private static int checkAppDuplicateSkipCount = 0;
    private static int checkAppMemorySkipCount = 0;
    private Thread thread;
    private volatile boolean interrupted = false;

    private static final String APP_APPEND_SYMBOL = "$";

    @Autowired
    private ClusterService clusterService;
    @Autowired
    private YarnAppService yarnAppService;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ClusterUserService clusterUserService;
    @Autowired
    private YarnConfig yarnConfig;

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
        checkAppDuplicateSkipCount ++;
        checkAppMemorySkipCount ++;
        Date now = new Date();
        Iterable<Cluster> clusters = clusterService.getAll();
        // 遍历集群
        for (Cluster cluster : clusters) {
            String clusterId = cluster.getId();
            // 请求yarn web url, 获取活跃应用
            List<HttpYarnApp> apps = YarnApiUtils.getActiveStateApps(cluster.getYarnUrl());
            //请求出错，不清理数据
            if (apps == null) {
                continue;
            }
            if (apps.isEmpty()) {
                // 清理数据
                // yarn web的信息获取是空就删除对应集群
                yarnAppService.deleteByClusterId(clusterId);
                continue;
            }
            // key：user $ queue $ script.getApp();
            Map<String, Script> scriptInfoMap = scriptService.getAppMap(clusterId);
            List<YarnApp> appInfos = new ArrayList<>();
            for (HttpYarnApp app : apps) {
                Script script;
                if (PATTERN.matcher(app.getName()).find()) {
                    script = scriptInfoMap.get(app.getUser() + APP_APPEND_SYMBOL + app.getQueue() + APP_APPEND_SYMBOL + app.getName().split("_instance")[0]);
                } else {
                    script = scriptInfoMap.get(app.getUser() + APP_APPEND_SYMBOL + app.getQueue() + APP_APPEND_SYMBOL + app.getName());
                }
                YarnApp yarnApp = new YarnApp();
                BeanUtils.copyProperties(app, yarnApp);
                yarnApp.setId(null);
                if (script != null) {
                    yarnApp.setUid(script.getUid());
                    yarnApp.setScriptId(script.getId());
                } else {
                    String queue = app.getQueue();
                    List<ClusterUser> clusterUsers = clusterUserService.getByClusterIdAndQueue(clusterId, queue);
                    if (clusterUsers.isEmpty()) {
                        if (queue.startsWith("root.")) {
                            queue = queue.substring(5);
                            clusterUsers =  clusterUserService.getByClusterIdAndQueue(clusterId, queue);
                        }
                    }
                    if (!clusterUsers.isEmpty()) {
                        boolean match = false;
                        for (ClusterUser clusterUser : clusterUsers) {
                            if (yarnApp.getUser().equals(clusterUser.getUser())) {
                                yarnApp.setUid(clusterUser.getUid());
                                match = true;
                                break;
                            }
                        }
                        if (!match) {
                            yarnApp.setUid(clusterUsers.get(0).getUid());
                        }
                    }
                }
                yarnApp.setClusterId(clusterId);
                yarnApp.setUpdateTime(now);
                yarnApp.setAppId(app.getId());
                yarnApp.setStartedTime(new Date(app.getStartedTime()));
                appInfos.add(yarnApp);
            }
            //先标记再删除
            List<YarnApp> yarnApps = yarnAppService.getByClusterId(clusterId);
            if (appInfos.size() > 0) {
                yarnAppService.saveAll(appInfos);
            }
            // 将该集群上原来的节点进行
            if (!CollectionUtils.isEmpty(yarnApps)) {
                List<String> ids = new ArrayList<>(yarnApps.size());
                yarnApps.forEach(item -> ids.add(item.getId()));
                yarnAppService.deleteById(ids);
            }
            // 当定时循环次数到达30次的时候
            if (checkAppDuplicateSkipCount >= 30) {
                checkAppDuplicate(cluster, apps, scriptInfoMap);
            }
            if (checkAppMemorySkipCount >= 300) {
                checkAppMemory(cluster, apps, scriptInfoMap);
            }
        }
        if (checkAppDuplicateSkipCount >= 30) {
            checkAppDuplicateSkipCount = 0;
        }
        if (checkAppMemorySkipCount >= 300) {
            checkAppMemorySkipCount = 0;
        }
    }

    /**
     * 检查重复应用
     * @param cluster
     * @param apps
     * @param scriptInfoMap
     */
    private void checkAppDuplicate(Cluster cluster, List<HttpYarnApp> apps, Map<String, Script> scriptInfoMap) {
        Map<String, List<HttpYarnApp>> appInstanceMap = new HashMap<>();
        for (HttpYarnApp app : apps) {
            String key = app.getUser() + APP_APPEND_SYMBOL + app.getQueue() + APP_APPEND_SYMBOL + app.getName();
            if (appInstanceMap.containsKey(key)) {
                appInstanceMap.get(key).add(app);
            } else {
                List<HttpYarnApp> httpYarnApps = new ArrayList<>();
                httpYarnApps.add(app);
                appInstanceMap.put(key, httpYarnApps);
            }
        }
        for (Map.Entry<String, List<HttpYarnApp>> stringListEntry : appInstanceMap.entrySet()) {
            String key = stringListEntry.getKey();
            List<HttpYarnApp> vals = stringListEntry.getValue();
            if (vals.size() > 1) {
                Script script = scriptInfoMap.get(key);
                HttpYarnApp httpYarnApp = vals.get(0);
                StringBuilder trackingUrl = new StringBuilder();
                vals.forEach(item -> trackingUrl.append(item.getTrackingUrl()).append(", "));
                String trackingUrls = trackingUrl.substring(0, trackingUrl.length() - 2);
                notice(cluster, script, httpYarnApp, trackingUrls, Constant.ERROR_TYPE_APP_DUPLICATE);
            }
        }
    }

    /**
     * 大内存应用检查
     * @param cluster
     * @param apps
     * @param scriptInfoMap key：user $ queue $ app  value: script
     */
    private void checkAppMemory(Cluster cluster, List<HttpYarnApp> apps, Map<String, Script> scriptInfoMap) {
        if (yarnConfig.getAppMemoryThreshold() <= 0) {
            return;
        }
        for (HttpYarnApp httpYarnApp : apps) {
            if ("RUNNING".equalsIgnoreCase(httpYarnApp.getState()) && httpYarnApp.getAllocatedMB() != null) {
                // 分配内存数值超过设定并且不是白名单
                if (httpYarnApp.getAllocatedMB() >= yarnConfig.getAppMemoryThreshold() && !yarnConfig.getAppWhiteList().contains(httpYarnApp.getName())) {
                    String key = httpYarnApp.getUser() + APP_APPEND_SYMBOL + httpYarnApp.getQueue() + APP_APPEND_SYMBOL + httpYarnApp.getName();
                    Script script = scriptInfoMap.get(key);
                    String trackingUrl = httpYarnApp.getTrackingUrl();
                    notice(cluster, script, httpYarnApp, trackingUrl, Constant.ERROR_TYPE_APP_MEMORY_OVERLIMIT);
                }
            }
        }
    }

}
