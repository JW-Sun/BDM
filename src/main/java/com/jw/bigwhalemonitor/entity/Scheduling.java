package com.jw.bigwhalemonitor.entity;

import java.util.Date;

public class Scheduling {
    private String id;

    private String uid;

    private Integer type;

    private String scriptIds;

    private Integer cycle;

    private Integer intervals;

    private Integer minute;

    private Integer hour;

    private String week;

    private String cron;

    private Date startTime;

    private Date endTime;

    private Boolean repeatSubmit;

    private Boolean exRestart;

    private Integer waitingBatches;

    private Boolean blockingRestart;

    private Date lastExecuteTime;

    private Boolean sendEmail;

    private String dingdingHooks;

    private Date createTime;

    private Date updateTime;

    private Boolean enabled;

    private String topology;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getScriptIds() {
        return scriptIds;
    }

    public void setScriptIds(String scriptIds) {
        this.scriptIds = scriptIds == null ? null : scriptIds.trim();
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getIntervals() {
        return intervals;
    }

    public void setIntervals(Integer intervals) {
        this.intervals = intervals;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getRepeatSubmit() {
        return repeatSubmit;
    }

    public void setRepeatSubmit(Boolean repeatSubmit) {
        this.repeatSubmit = repeatSubmit;
    }

    public Boolean getExRestart() {
        return exRestart;
    }

    public void setExRestart(Boolean exRestart) {
        this.exRestart = exRestart;
    }

    public Integer getWaitingBatches() {
        return waitingBatches;
    }

    public void setWaitingBatches(Integer waitingBatches) {
        this.waitingBatches = waitingBatches;
    }

    public Boolean getBlockingRestart() {
        return blockingRestart;
    }

    public void setBlockingRestart(Boolean blockingRestart) {
        this.blockingRestart = blockingRestart;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getDingdingHooks() {
        return dingdingHooks;
    }

    public void setDingdingHooks(String dingdingHooks) {
        this.dingdingHooks = dingdingHooks == null ? null : dingdingHooks.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology == null ? null : topology.trim();
    }
}