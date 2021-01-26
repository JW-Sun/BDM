package com.jw.bigwhalemonitor.listener;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.service.SchedulingService;
import com.jw.bigwhalemonitor.task.common.CmdRecordAppStatusUpdateJob;
import com.jw.bigwhalemonitor.task.common.CmdRecordClearJob;
import com.jw.bigwhalemonitor.task.common.CmdRecordTimeoutJob;
import com.jw.bigwhalemonitor.task.common.RefreshActiveStateAppsJob;
import com.jw.bigwhalemonitor.task.common.TestJob;
import com.jw.bigwhalemonitor.task.monitor.AbstractMonitorRunner;
import com.jw.bigwhalemonitor.task.timed.TimedTask;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "pro")
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(ApplicationReadyListener.class);

    @Autowired
    private SchedulingService schedulingService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.warn("Starting necessary task");
        //启动常驻任务
        startResidentMission();
        //启动任务调度
        startScheduling();
    }

    private void startResidentMission() {
        try {

            // 测试
            // SchedulerUtils.schedulerCronJob(TestJob.class, "*/1 * * * * ?");

            //启动yarn活跃应用列表更新任务（包含应用重复检测和大内存应用检测）
            SchedulerUtils.schedulerCronJob(RefreshActiveStateAppsJob.class, "*/10 * * * * ?");

            //启动脚本执行超时处理任务
            SchedulerUtils.schedulerCronJob(CmdRecordTimeoutJob.class, "*/1 * * * * ?");

            //启动批处理应用状态更新任务（包含提交子脚本）
            SchedulerUtils.schedulerCronJob(CmdRecordAppStatusUpdateJob.class, "*/10 * * * * ?");

            //启动执行记录清理任务
            SchedulerUtils.schedulerCronJob(CmdRecordClearJob.class, "0 0 0 */1 * ?");

            //平台执行超时处理任务
            // SchedulerUtils.schedulerCronJob(PlatformTimeoutJob.class, "*/10 * * * * ?");
        } catch (SchedulerException e) {
            logger.error("schedule submit error", e);
        }
    }

    private void startScheduling() {
        List<Scheduling> schedulings = schedulingService.getByEnable(true);
        schedulings.forEach(scheduling -> {
            try {
                if (new Date().after(scheduling.getEndTime())) {
                    SchedulerUtils.deleteScheduler(scheduling.getId(), Constant.JobGroup.TIMED);
                    scheduling.setEnabled(false);
                    schedulingService.save(scheduling);
                } else {
                    if (scheduling.getType() == Constant.SCHEDULING_TYPE_BATCH) {
                        TimedTask.build(scheduling);
                    } else {
                        AbstractMonitorRunner.build(scheduling);
                    }
                }
            } catch (SchedulerException e) {
                logger.error("schedule submit error", e);
            }
        });
    }

}

