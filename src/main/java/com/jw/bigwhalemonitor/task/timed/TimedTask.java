package com.jw.bigwhalemonitor.task.timed;

import com.alibaba.fastjson.JSON;
import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import com.jw.bigwhalemonitor.service.SchedulingService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.cmd.CmdRecordRunner;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@DisallowConcurrentExecution
public class TimedTask implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimedTask.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    private CmdRecordService cmdRecordService;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private SchedulingService schedulingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String schedulingId = jobExecutionContext.getJobDetail().getKey().getName();
        Scheduling scheduling = schedulingService.getById(schedulingId);
        if (scheduling.getLastExecuteTime() != null && !scheduling.getRepeatSubmit()) {
            // 检查是否最后一次完成
            if (!isLastTimeCompleted(scheduling)) {
                return;
            }
        }
        String now = dateFormat.format(new Date());
        try {
            scheduling.setLastExecuteTime(dateFormat.parse(now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        schedulingService.save(scheduling);
        Map<String, String> nodeIdToScriptId = scheduling.analyzeNextNode(null);
        nodeIdToScriptId.forEach((nodeId, scriptId) -> {
            Script script = scriptService.getById(scriptId);
            CmdRecordWithBLOBs cmdRecord = new CmdRecordWithBLOBs();
            cmdRecord.setUid(scheduling.getUid());
            cmdRecord.setScriptId(scriptId);
            cmdRecord.setStatus(Constant.EXEC_STATUS_UNSTART);
            cmdRecord.setAgentId(script.getAgentId());
            cmdRecord.setClusterId(script.getClusterId());
            cmdRecord.setSchedulingId(scheduling.getId());
            cmdRecord.setSchedulingInstanceId(now);
            cmdRecord.setSchedulingNodeId(nodeId);
            cmdRecord.setContent(script.getScript());
            cmdRecord.setTimeout(script.getTimeout());
            cmdRecord.setCreateTime(new Date());

            if (!jobExecutionContext.getMergedJobDataMap().isEmpty()) {
                cmdRecord.setArgs(JSON.toJSONString(jobExecutionContext.getMergedJobDataMap()));
            }
            cmdRecordService.save(cmdRecord);
            //提交任务
            try {
                CmdRecordRunner.build(cmdRecord);
            } catch (SchedulerException e) {
                LOGGER.error("schedule submit error", e);
            }
        });
    }

    private boolean isLastTimeCompleted(Scheduling scheduling) {
        // 按照要求进行搜索, scheduling的ID以及最后完成的执行时间
        List<CmdRecordWithBLOBs> cmdRecords = cmdRecordService.getByQuery2(scheduling.getId(), dateFormat.format(scheduling.getLastExecuteTime()));
        //非程序意外退出的情况下，相关执行记录不会为空
        if (cmdRecords.isEmpty()) {
            return true;
        }
        for (CmdRecord cmdRecord : cmdRecords) {
            // 如果任务的状态是没开始或者正在运行
            if (cmdRecord.getStatus() == Constant.EXEC_STATUS_UNSTART || cmdRecord.getStatus() == Constant.EXEC_STATUS_DOING) {
                return false;
            }
        }
        // 检查脚本的状态
        if (cmdRecords.size() == scheduling.getScriptIds().split(",").length) {
            for (CmdRecord cmdRecord : cmdRecords) {
                Script script = scriptService.getById(cmdRecord.getScriptId());
                if (isYarnBatch(script)) {
                    // 没有定义就是没有状态不明
                    if ("UNDEFINED".equals(cmdRecord.getJobFinalStatus())) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            List<CmdRecordWithBLOBs> lastCmdRecords = new ArrayList<>();
            // 填充节点树的末尾
            filterNodeTreeLast(scheduling, cmdRecords, lastCmdRecords, null);

            for (CmdRecord lastCmdRecord : lastCmdRecords) {
                Script script = scriptService.getById(lastCmdRecord.getScriptId());
                if (isYarnBatch(script)) {
                    if ("UNDEFINED".equals(lastCmdRecord.getJobFinalStatus()) || "SUCCEEDED".equals(lastCmdRecord.getJobFinalStatus())) {
                        return false;
                    }
                } else {
                    if (Constant.EXEC_STATUS_FINISH == lastCmdRecord.getStatus())  {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private void filterNodeTreeLast(Scheduling scheduling, List<CmdRecordWithBLOBs> cmdRecords, List<CmdRecordWithBLOBs> lastCmdRecords, String currentNodeId) {
        Map<String, String> nodeIdToScriptId = scheduling.analyzeNextNode(currentNodeId);
        for (Map.Entry<String, String> entry : nodeIdToScriptId.entrySet()) {
            CmdRecordWithBLOBs currentCmdRecord = null;
            boolean match = false;
            for (CmdRecordWithBLOBs cmdRecord : cmdRecords) {
                if (cmdRecord.getSchedulingNodeId().equals(currentNodeId)) {
                    currentCmdRecord = cmdRecord;
                }
                if (cmdRecord.getSchedulingNodeId().equals(entry.getKey())) {
                    match = true;
                }
            }
            if (match) {
                filterNodeTreeLast(scheduling, cmdRecords, lastCmdRecords, entry.getKey());
            } else {
                if (currentCmdRecord != null) {
                    lastCmdRecords.add(currentCmdRecord);
                }
            }
        }
    }

    private boolean isYarnBatch(Script script) {
        return script.getType() == Constant.SCRIPT_TYPE_SPARK_BATCH || script.getType() == Constant.SCRIPT_TYPE_FLINK_BATCH;
    }

    public static void build(Scheduling scheduling) throws SchedulerException {
        SchedulerUtils.schedulerCronJob(TimedTask.class,
                scheduling.getId(),
                Constant.JobGroup.TIMED,
                scheduling.generateCron(),
                scheduling.getStartTime(),
                scheduling.getEndTime(),
                null);
    }

}
