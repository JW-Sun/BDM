package com.jw.bigwhalemonitor.task;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.CmdRecordService;
import com.jw.bigwhalemonitor.service.SchedulingService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.task.cmd.CmdRecordRunner;
import com.mysql.jdbc.TimeUtil;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/***
 *  继承报警的模块，抽象出Cmd任务命令的任务
 */
public abstract class AbstractCmdRecordTask extends AbstractNoticeableTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCmdRecordTask.class);

    @Autowired
    protected CmdRecordService cmdRecordService;
    @Autowired
    protected SchedulingService schedulingService;

    /***
     *  提交下一个脚本任务
     * @param cmdRecord
     * @param scheduling
     * @param scriptService
     */
    public void submitNextCmdRecord2(CmdRecord cmdRecord, Scheduling scheduling, ScriptService scriptService) {
        if (scheduling != null) {
            // 这个任务的创造事件是早于scheduling的更新时间
            if (cmdRecord.getCreateTime().before(scheduling.getUpdateTime())) {
                return;
            }
            Map<String, String> nodeIdToScriptId = scheduling.analyzeNextNode(cmdRecord.getSchedulingNodeId());
            // 如果没有对应的nodeId对应的scriptId，就直接进行返回
            if (nodeIdToScriptId.isEmpty()) {
                return;
            }
            for (Map.Entry<String, String> entry : nodeIdToScriptId.entrySet()) {
                String nodeId = entry.getKey();
                String scriptId = entry.getValue();
                // 找到script
                Script scriptById = scriptService.getById(scriptId);
                CmdRecordWithBLOBs cmdRecordWithBLOBs = new CmdRecordWithBLOBs();
                cmdRecordWithBLOBs.setParentId(cmdRecord.getParentId());
                cmdRecordWithBLOBs.setUid(scheduling.getUid());
                cmdRecordWithBLOBs.setCreateTime(new Date());
                cmdRecordWithBLOBs.setContent(scriptById.getScript());
                cmdRecordWithBLOBs.setTimeout(scriptById.getTimeout());
                cmdRecordWithBLOBs.setStatus(Constant.EXEC_STATUS_UNSTART);
                cmdRecordWithBLOBs.setAgentId(scriptById.getAgentId());
                cmdRecordWithBLOBs.setClusterId(scriptById.getClusterId());
                cmdRecordWithBLOBs.setSchedulingId(scheduling.getId());
                cmdRecordWithBLOBs.setSchedulingInstanceId(cmdRecord.getSchedulingInstanceId());
                cmdRecordWithBLOBs.setSchedulingNodeId(nodeId);
                cmdRecordWithBLOBs.setArgs(cmdRecord.getArgs());
                // 将构造生成的新的cmdRecord进行保存
                cmdRecordService.save(cmdRecordWithBLOBs);

                // 将组装好的cmd任务进行执行
                try {
                    CmdRecordRunner.buildBlob(cmdRecordWithBLOBs);
                } catch (SchedulerException e) {
                    LOGGER.error("Cmd record submit fail ", e);
                }
            }
        }
    }

    /**
     * 提交下一个任务
     * @param cmdRecord
     * @param scheduling
     * @param scriptService
     */
    // protected void submitNextCmdRecord(CmdRecord cmdRecord, Scheduling scheduling, ScriptService scriptService) {
    //     if (scheduling != null) {
    //         //在上一次脚本任务链未执行完毕的情况下，更新过调度，则跳过余下脚本任务
    //         if (cmdRecord.getCreateTime().before(scheduling.getUpdateTime())) {
    //             return;
    //         }
    //         Map<String, String> nodeIdToScriptId = scheduling.analyzeNextNode(cmdRecord.getSchedulingNodeId());
    //         if (nodeIdToScriptId.isEmpty()) {
    //             return;
    //         }
    //         for (Map.Entry<String, String> entry : nodeIdToScriptId.entrySet()) {
    //             String nodeId = entry.getKey();
    //             String scriptId = entry.getValue();
    //             Script script = scriptService.findById(scriptId);
    //             CmdRecord record = CmdRecord.builder()
    //                     .parentId(cmdRecord.getId())
    //                     .uid(scheduling.getUid())
    //                     .scriptId(scriptId)
    //                     .createTime(new Date())
    //                     .content(script.getScript())
    //                     .timeout(script.getTimeout())
    //                     .status(Constant.EXEC_STATUS_UNSTART)
    //                     .agentId(script.getAgentId())
    //                     .clusterId(script.getClusterId())
    //                     .schedulingId(scheduling.getId())
    //                     .schedulingInstanceId(cmdRecord.getSchedulingInstanceId())
    //                     .schedulingNodeId(nodeId)
    //                     .args(cmdRecord.getArgs())
    //                     .build();
    //             record = cmdRecordService.save(record);
    //             try {
    //                 CmdRecordRunner.build(record);
    //             } catch (SchedulerException e) {
    //                 LOGGER.error("schedule submit error", e);
    //             }
    //         }
    //     }
    // }

}
