package com.jw.bigwhalemonitor.util;


import com.jw.bigwhalemonitor.common.Constant;
import netscape.security.UserTarget;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import java.util.Date;

public class SchedulerUtils {

    private static Scheduler scheduler;

    static {
        // SpringContextUtils是实现了ApplicationContextAware的接口主要的目的就是可以访问到spring容器中全部的bean
        if (SpringContextUtils.getApplicationContext() != null) {
            scheduler = SpringContextUtils.getBean(Scheduler.class);
        } else {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            try {
                scheduler = schedulerFactory.getScheduler();
                scheduler.start();
            } catch (SchedulerException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    public SchedulerUtils() {}

    public static Scheduler getScheduler() {
        return scheduler;
    }

    /* ============带cron表达式的调度值============== */

    public static void setScheduler(Scheduler scheduler) {
        SchedulerUtils.scheduler = scheduler;
    }

    public static void schedulerCronJob(Class<? extends Job> jobClass, String cronExpression) throws SchedulerException {
        schedulerCronJob(jobClass, jobClass.getSimpleName(), cronExpression);
    }

    public static void schedulerCronJob(Class<? extends Job> jobClass, String simpleName, String cronExpression) throws SchedulerException {
        schedulerCronJob(jobClass, simpleName, Constant.JobGroup.COMMON, cronExpression);
    }

    public static void schedulerCronJob(Class<? extends Job> jobClass, String simpleName, String common, String cronExpression) throws SchedulerException {
        schedulerCronJob(jobClass, simpleName, common, cronExpression, null);
    }

    public static void schedulerCronJob(Class<? extends Job> jobClass, String simpleName, String group, String cronExpression, JobDataMap jobDataMap) throws SchedulerException {
        // 定义一个quartz的job key的对象，进行
        JobKey jobKey = new JobKey(simpleName, group);
        // 检查scheduler中是否有已经存在的jobKey，只有没有已经存在的情况下，才能继续后面的任务
        if (!scheduler.checkExists(jobKey)) {
            // jobBuilder就是可理解为quartz任务的创建管理的原始操作
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            // 准备为jobBuilder设置一个识别组件
            jobBuilder.withIdentity(jobKey);
            // job相关的data信息
            if (jobDataMap != null && jobDataMap.size() > 0) {
                jobBuilder.setJobData(jobDataMap);
            }
            // 生成对应的jobDetail
            JobDetail jobDetail = jobBuilder.build();
            // 按照cron表达式进行该建立时间的进程
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder);
            Trigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    // 带有开始时间和结束事件的调度
    public static void scheduleCronJob(Class<? extends Job> jobClass, String simpleName, String group, String cronExpression, Date startDate, Date endDate, JobDataMap jobDataMap) throws SchedulerException {
        JobKey jobKey = new JobKey(simpleName, group);
        if (!scheduler.checkExists(jobKey)) {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobKey);
            if (jobDataMap != null && jobDataMap.size() > 0) {
                jobBuilder.setJobData(jobDataMap);
            }
            JobDetail jobDetail = jobBuilder.build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder);
            if (startDate != null) {
                triggerBuilder.startAt(startDate);
            } else {
                triggerBuilder.startNow();
            }
            if (endDate != null) {
                triggerBuilder.endAt(endDate);
            }
            Trigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    /*=======立即执行的simpleJob========*/
    public static void schedulerSimpleJob(Class<? extends Job> jobClass) throws SchedulerException {
        schedulerSimpleJob(jobClass, jobClass.getSimpleName());
    }

    public static void schedulerSimpleJob(Class<? extends Job> jobClass, String simpleName) throws SchedulerException {
        schedulerSimpleJob(jobClass, simpleName, 0L, 0);
    }

    /***
     *
     * @param jobClass
     * @param simpleName
     * @param intervalMillisecond 间隔 time
     * @param repeatCount
     */
    public static void schedulerSimpleJob(Class<? extends Job> jobClass, String simpleName, long intervalMillisecond, int repeatCount) throws SchedulerException {
        schedulerSimpleJob(jobClass, simpleName, Constant.JobGroup.COMMON, intervalMillisecond, repeatCount);
    }

    public static void schedulerSimpleJob(Class<? extends Job> jobClass, String simpleName, String group, long intervalMillisecond, int repeatCount) throws SchedulerException {
        schedulerSimpleJob(jobClass, simpleName, group, intervalMillisecond, repeatCount, null);
    }

    public static void schedulerSimpleJob(Class<? extends Job> jobClass, String simpleName, String group, long intervalMillisecond, int repeatCount, JobDataMap jobDataMap) throws SchedulerException {
        schedulerSimpleJob(jobClass, simpleName, group, intervalMillisecond, repeatCount, jobDataMap, null, null);
    }

    public static void schedulerSimpleJob(Class<? extends Job> jobClass, String simpleName, String group, long intervalMillisecond, int repeatCount, JobDataMap jobDataMap, Date startDate, Date endDate) throws SchedulerException {
        JobKey jobKey = new JobKey(simpleName, group);
        if (!scheduler.checkExists(jobKey)) {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobKey);
            // 为job任务的数据进行配置
            if (jobDataMap != null && jobDataMap.size() > 0) {
                jobBuilder.setJobData(jobDataMap);
            }
            JobDetail jobDetail = jobBuilder.build();

            // 创建SimpleScheduler的调度进程。
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            simpleScheduleBuilder.withIntervalInMilliseconds(intervalMillisecond);
            if (repeatCount > 0) {
                // 按照重复的次数进行设置
                simpleScheduleBuilder.withRepeatCount(repeatCount);
            } else {
                // 无休止的重复
                simpleScheduleBuilder.repeatForever();
            }
            TriggerBuilder<SimpleTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(simpleScheduleBuilder);
            if (startDate != null) {
                triggerBuilder.startAt(startDate);
            } else {
                triggerBuilder.startNow();
            }
            if (endDate != null) {
                triggerBuilder.endAt(endDate);
            }
            SimpleTrigger build = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, build);
        }
    }

    public static void deleteScheduler(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    public static boolean checkScheduler(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        return scheduler.checkExists(jobKey);
    }

}

class Solution {
    // 四层遍历 f(i, j, u) u是子串的长度 代表的是 s1 (i, i + u - 1)子串 与 s2 (j, j + u - 1)子串是否是扰乱字符串
    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return true;
        }
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 != len2) {
            return false;
        }
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        boolean[][][] f = new boolean[len1][len1][len1 + 1];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (c1[i] == c2[j]) {
                    f[i][j][1] = true;
                }
            }
        }
        // 先遍历每一个区间的长度
        for (int ll = 2; ll <= len1; ll++) {
            // 然后遍历两个字符串中的每一位进行考虑
            for (int i = 0; i + ll - 1 < len1; i++) {
                for (int j = 0; j + ll - 1 < len2; j++) {
                    // 然后枚举可以分割的长度
                    for (int u = 1; u < ll; u++) {
                        // 1. 都在左边
                        boolean b1 = f[i][j][u] && f[i + u][j + u][ll - u];
                        // 2. 分割长度一左一右 012
                        boolean b2 = f[i][j + ll - u][u] && f[i + u][j][ll - u];
                        f[i][j][ll] = b1 || b2;
                    }
                }
            }
        }

        return f[0][0][len1];
    }
}

/**
 * @author Suxy
 * @date 2019/8/30
 * @description file description
 */
class SchedulerUtil {

    private static Scheduler scheduler;

    static {
        if (SpringContextUtils.getApplicationContext() != null) {
            scheduler = SpringContextUtils.getBean(Scheduler.class);
        } else {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            try {
                scheduler = schedulerFactory.getScheduler();
                scheduler.start();
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private SchedulerUtil() {

    }

    public static Scheduler getScheduler() {
        return scheduler;
    }

    public static void scheduleCornJob(Class<? extends Job> jobClass, String cronExpression) throws SchedulerException {
        scheduleCornJob(jobClass, jobClass.getSimpleName(), cronExpression);
    }

    public static void scheduleCornJob(Class<? extends Job> jobClass, String name, String cronExpression) throws SchedulerException {
        scheduleCornJob(jobClass, name, Constant.JobGroup.COMMON, cronExpression);
    }

    public static void scheduleCornJob(Class<? extends Job> jobClass, String name, String group, String cronExpression) throws SchedulerException {
        scheduleCornJob(jobClass, name, group, cronExpression, null);
    }

    public static void scheduleCornJob(Class<? extends Job> jobClass, String name, String group, String cronExpression, JobDataMap jobDataMap) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (!scheduler.checkExists(jobKey)) {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobKey);
            if (jobDataMap != null && !jobDataMap.isEmpty()) {
                jobBuilder.setJobData(jobDataMap);
            }
            JobDetail jobDetail = jobBuilder.build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder);
            CronTrigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public static void scheduleCornJob(Class<? extends Job> jobClass, String name, String group, String cronExpression, JobDataMap jobDataMap, Date startDate, Date endDate) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (!scheduler.checkExists(jobKey)) {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobKey);
            if (jobDataMap != null && !jobDataMap.isEmpty()) {
                jobBuilder.setJobData(jobDataMap);
            }
            JobDetail jobDetail = jobBuilder.build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder);
            if (startDate != null) {
                triggerBuilder.startAt(startDate);
            } else {
                triggerBuilder.startNow();
            }
            if (endDate != null) {
                triggerBuilder.endAt(endDate);
            }
            CronTrigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    /**
     * 默认立即执行且只执行一次
     * @param jobClass
     * @throws SchedulerException
     */
    public static void scheduleSimpleJob(Class<? extends Job> jobClass) throws SchedulerException {
        scheduleSimpleJob(jobClass, jobClass.getSimpleName());
    }

    public static void scheduleSimpleJob(Class<? extends Job> jobClass, String name) throws SchedulerException {
        scheduleSimpleJob(jobClass, name, 0, 0);
    }

    /**
     * @param jobClass
     * @param name
     * @param intervalInMilliseconds 执行间隔
     * @param repeatCount 重复次数，小于0的时候重复执行
     * @throws SchedulerException
     */
    public static void scheduleSimpleJob(Class<? extends Job> jobClass, String name, long intervalInMilliseconds, int repeatCount) throws SchedulerException {
        scheduleSimpleJob(jobClass, name, Constant.JobGroup.COMMON, intervalInMilliseconds, repeatCount);
    }

    public static void scheduleSimpleJob(Class<? extends Job> jobClass, String name, String group, long intervalInMilliseconds, int repeatCount) throws SchedulerException {
        scheduleSimpleJob(jobClass, name, group, intervalInMilliseconds, repeatCount, null);
    }

    public static void scheduleSimpleJob(Class<? extends Job> jobClass, String name, String group, long intervalInMilliseconds, int repeatCount, JobDataMap jobDataMap) throws SchedulerException {
        scheduleSimpleJob(jobClass, name, group, intervalInMilliseconds, repeatCount, jobDataMap, null, null);
    }

    public static void scheduleSimpleJob(Class<? extends Job> jobClass, String name, String group, long intervalInMilliseconds, int repeatCount, JobDataMap jobDataMap, Date startDate, Date endDate) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (!scheduler.checkExists(jobKey)) {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            jobBuilder.withIdentity(jobKey);
            if (jobDataMap != null && !jobDataMap.isEmpty()) {
                jobBuilder.setJobData(jobDataMap);
            }
            JobDetail jobDetail = jobBuilder.build();
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            simpleScheduleBuilder.withIntervalInMilliseconds(intervalInMilliseconds);
            if (repeatCount >= 0) {
                simpleScheduleBuilder.withRepeatCount(repeatCount);
            } else {
                simpleScheduleBuilder.repeatForever();
            }
            TriggerBuilder<SimpleTrigger> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(simpleScheduleBuilder);
            if (startDate != null) {
                triggerBuilder.startAt(startDate);
            } else {
                triggerBuilder.startNow();
            }
            if (endDate != null) {
                triggerBuilder.endAt(endDate);
            }
            SimpleTrigger trigger = triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public static void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }

    public static boolean checkExists(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        return scheduler.checkExists(jobKey);
    }

}
