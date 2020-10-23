package com.jw.bigwhalemonitor.entity;

public class QrtzSchedulerState extends QrtzSchedulerStateKey {
    private Long lastCheckinTime;

    private Long checkinInterval;

    public Long getLastCheckinTime() {
        return lastCheckinTime;
    }

    public void setLastCheckinTime(Long lastCheckinTime) {
        this.lastCheckinTime = lastCheckinTime;
    }

    public Long getCheckinInterval() {
        return checkinInterval;
    }

    public void setCheckinInterval(Long checkinInterval) {
        this.checkinInterval = checkinInterval;
    }
}