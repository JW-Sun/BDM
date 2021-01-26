package com.jw.bigwhalemonitor.task.common;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.CmdRecordWithBLOBs;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.task.AbstractCmdRecordTask;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;


@DisallowConcurrentExecution
public class CmdRecordTimeoutJob extends AbstractCmdRecordTask implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmdRecordTimeoutJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //未开始执行和正在执行的
        Collection<CmdRecordWithBLOBs> cmdRecords = cmdRecordService.getByQuery3();
        if (CollectionUtils.isEmpty(cmdRecords)) {
            return;
        }
        for (CmdRecordWithBLOBs cmdRecord : cmdRecords) {
            Date now = new Date();
            if (cmdRecord.getTimeout() == null) {
                cmdRecord.setTimeout(5);
            }
            int timeout = cmdRecord.getTimeout();
            long ago = DateUtils.addMinutes(now, -timeout).getTime();
            boolean timeoutFlag = false;
            //执行超时
            if (cmdRecord.getStatus() == Constant.EXEC_STATUS_DOING
                    && cmdRecord.getStartTime().getTime() <= ago) {
                cmdRecord.setStatus(Constant.EXEC_STATUS_TIMEOUT);
                timeoutFlag = true;
            } else {
                // cmdRecord创建时间超时也算作超时
                if (cmdRecord.getCreateTime().getTime() <= ago) {
                    cmdRecord.setStatus(Constant.EXEC_STATUS_TIMEOUT);
                    timeoutFlag = true;
                }
            }
            if (timeoutFlag) {
                Scheduling scheduling = StringUtils.isNotBlank(cmdRecord.getSchedulingId()) ? schedulingService.getById(cmdRecord.getSchedulingId()) : null;
                notice(cmdRecord, scheduling, null, Constant.ERROR_TYPE_TIMEOUT);
                cmdRecordService.save(cmdRecord);
                //处理调度
                try {
                    JobKey jobKey = new JobKey(cmdRecord.getId(), Constant.JobGroup.CMD);
                    if (cmdRecord.getStatus() == Constant.EXEC_STATUS_UNSTART) {
                        SchedulerUtils.getScheduler().deleteJob(jobKey);
                    } else {
                        SchedulerUtils.getScheduler().interrupt(jobKey);
                    }
                } catch (SchedulerException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

}
