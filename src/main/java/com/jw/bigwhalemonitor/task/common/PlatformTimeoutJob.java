// package com.jw.bigwhalemonitor.task.common;
//
//
// import com.jw.bigwhalemonitor.common.Constant;
// import com.jw.bigwhalemonitor.entity.Scheduling;
// import com.jw.bigwhalemonitor.entity.YarnApp;
// import com.jw.bigwhalemonitor.service.SchedulingService;
// import com.jw.bigwhalemonitor.service.YarnAppService;
// import com.jw.bigwhalemonitor.service.cluster.ClusterService;
// import com.jw.bigwhalemonitor.service.script.ScriptService;
// import com.jw.bigwhalemonitor.util.MsgTools;
// import com.jw.bigwhalemonitor.util.SchedulerUtils;
// import com.jw.bigwhalemonitor.util.YarnApiUtils;
// import org.apache.commons.lang.time.DateUtils;
// import org.quartz.DisallowConcurrentExecution;
// import org.quartz.Job;
// import org.quartz.JobExecutionContext;
// import org.quartz.JobKey;
// import org.quartz.SchedulerException;
// import org.quartz.UnableToInterruptJobException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import java.util.Date;
// import java.util.List;
//
//
// @DisallowConcurrentExecution
// public class PlatformTimeoutJob implements Job {
//
//     private static final Logger LOGGER = LoggerFactory.getLogger(PlatformTimeoutJob.class);
//
//     @Autowired
//     private NoticeService noticeService;
//     @Autowired
//     private SchedulingService schedulingService;
//     @Autowired
//     private YarnAppService yarnAppService;
//     @Autowired
//     private ClusterService clusterService;
//     @Autowired
//     private ScriptService scriptService;
//
//     @Override
//     public void execute(JobExecutionContext jobExecutionContext) {
//         List<JobExecutionContext> executionContexts;
//         try {
//             executionContexts = SchedulerUtils.getScheduler().getCurrentlyExecutingJobs();
//         } catch (SchedulerException e) {
//             LOGGER.error(e.getMessage(), e);
//             return;
//         }
//         Date current = new Date();
//         Date tenMinBefore = DateUtils.addMinutes(current, -10);
//         executionContexts.forEach(executionContext -> {
//             if (executionContext.getFireTime().before(tenMinBefore)) {
//                 JobKey jobKey = executionContext.getJobDetail().getKey();
//                 //监控任务
//                 if (Constant.JobGroup.MONITOR.equals(jobKey.getGroup())) {
//                     //杀掉应用
//                     Scheduling scheduling = schedulingService.findById(jobKey.getName());
//                     YarnApp appInfo = yarnAppService.findOneByQuery("scriptId=" + scheduling.getScriptIds());
//                     if (appInfo != null) {
//                         Cluster cluster = clusterService.findById(appInfo.getClusterId());
//                         YarnApiUtils.killApp(cluster.getYarnUrl(), appInfo.getAppId());
//                         yarnAppService.deleteById(appInfo.getId());
//                     }
//                     Script script = scriptService.findById(scheduling.getScriptIds());
//                     String msg = MsgTools.getPlainErrorMsg(null, null, null, "调度平台-监控任务（" + script.getName() + "）", "任务运行超时");
//                     noticeService.sendDingding(new String[0], msg);
//                     try {
//                         SchedulerUtils.getScheduler().interrupt(jobKey);
//                     } catch (UnableToInterruptJobException e) {
//                         LOGGER.error(e.getMessage(), e);
//                     }
//                 }
//                 //yarn应用列表更新和离线应用状态更新
//                 if (Constant.JobGroup.COMMON.equals(jobKey.getGroup())) {
//                     if (RefreshActiveStateAppsJob.class.getSimpleName().equals(jobKey.getName())) {
//                         String msg = MsgTools.getPlainErrorMsg(null, null, null, "调度平台-Yarn应用列表更新任务", "任务运行超时");
//                         noticeService.sendDingding(new String[0], msg);
//                     }
//                     if (CmdRecordAppStatusUpdateJob.class.getSimpleName().equals(jobKey.getName())) {
//                         String msg = MsgTools.getPlainErrorMsg(null, null, null, "调度平台-离线应用状态更新任务", "任务运行超时");
//                         noticeService.sendDingding(new String[0], msg);
//                     }
//                     try {
//                         SchedulerUtils.getScheduler().interrupt(jobKey);
//                     } catch (UnableToInterruptJobException e) {
//                         LOGGER.error(e.getMessage(), e);
//                     }
//                 }
//             }
//         });
//     }
// }
