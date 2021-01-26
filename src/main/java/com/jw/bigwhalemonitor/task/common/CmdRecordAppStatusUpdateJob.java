package com.jw.bigwhalemonitor.task.common;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.HttpYarnApp;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.service.YarnAppService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.AbstractCmdRecordTask;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;


@DisallowConcurrentExecution
public class CmdRecordAppStatusUpdateJob extends AbstractCmdRecordTask implements InterruptableJob {

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private Thread thread;
    private volatile boolean interrupted = false;

    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private YarnAppService yarnAppService;

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
        // 获得job最终任务状态是UNDEFINED的cmd记录
        Collection<CmdRecordWithBLOBs> records = cmdRecordService.getByJobFinalStatus("UNDEFINED");
        if (CollectionUtils.isEmpty(records)) {
            return;
        }
        for (CmdRecordWithBLOBs cmdRecord : records) {
            Cluster cluster = clusterService.getById(cmdRecord.getClusterId());
            Script script = scriptService.getById(cmdRecord.getScriptId());
            HttpYarnApp httpYarnApp = null;
            if (cmdRecord.getJobId() != null) {
                YarnApp yarnApp = yarnAppService.getByScriptIdAndAppId(cmdRecord.getScriptId(), cmdRecord.getJobId());
                if (yarnApp != null) {
                    continue;
                }
                httpYarnApp = YarnApiUtils.getApp(cluster.getYarnUrl(), cmdRecord.getJobId());
                if (httpYarnApp != null && "UNDEFINED".equals(httpYarnApp.getFinalStatus())) {
                    continue;
                }
                updateStatus(cmdRecord, script, httpYarnApp);
            } else {
                YarnApp yarnApp = yarnAppService.getByScriptIdAndName(cmdRecord.getScriptId(), script.getApp() + "_instance" + dateFormat.format(cmdRecord.getStartTime()));
                if (yarnApp != null) {
                    continue;
                }
                httpYarnApp = YarnApiUtils.getActiveApp(cluster.getYarnUrl(), script.getUser(), script.getQueue(), script.getApp() + "_instance" + dateFormat.format(cmdRecord.getStartTime()), 3);
                if (httpYarnApp != null) {
                    continue;
                }
                httpYarnApp = YarnApiUtils.getLastNoActiveApp(cluster.getYarnUrl(), script.getUser(), script.getQueue(), script.getApp(), 3);
                updateStatus(cmdRecord, script, httpYarnApp);
            }
        }
    }

    private void updateStatus(CmdRecordWithBLOBs cmdRecord, Script script, HttpYarnApp httpYarnApp) {
        if (httpYarnApp != null) {
            Scheduling scheduling = StringUtils.isNotBlank(cmdRecord.getSchedulingId()) ? schedulingService.getById(cmdRecord.getSchedulingId()) : null;
            String finalStatus = httpYarnApp.getFinalStatus();
            cmdRecord.setJobFinalStatus(finalStatus);
            if ("SUCCEEDED".equals(finalStatus)) {
                //提交子任务
                submitNextCmdRecord2(cmdRecord, scheduling, scriptService);
            } else {
                if (script.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH) {
                    notice(cmdRecord, scheduling, httpYarnApp.getId(), String.format(Constant.ERROR_TYPE_SPARK_BATCH_UNUSUAL, finalStatus));
                } else {
                    notice(cmdRecord, scheduling, httpYarnApp.getId(), String.format(Constant.ERROR_TYPE_FLINK_BATCH_UNUSUAL, finalStatus));
                }
            }
        } else {
            cmdRecord.setJobFinalStatus("UNKNOWN");
        }
        cmdRecordService.save(cmdRecord);
    }
}
