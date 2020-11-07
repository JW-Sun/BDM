package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.Scheduling;
import org.quartz.Scheduler;

import java.util.List;

public interface SchedulerService {
    List<Scheduling> getByScriptId(String id);
}
