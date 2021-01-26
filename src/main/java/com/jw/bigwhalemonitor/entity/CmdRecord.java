package com.jw.bigwhalemonitor.entity;

import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

public class CmdRecord implements Serializable {
    private String id;

    private String parentId;

    private String agentId;

    private String agentInstance;

    private String scriptId;

    private Integer status;

    private Integer timeout;

    private String uid;

    private String clusterId;

    private String schedulingId;

    private String schedulingInstanceId;

    private String schedulingNodeId;

    private Date createTime;

    private Date startTime;

    private Date finishTime;

    private String jobId;

    private String jobFinalStatus;

    private String url;

    private String args;

    public CmdRecord() {
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getAgentInstance() {
        return agentInstance;
    }

    public void setAgentInstance(String agentInstance) {
        this.agentInstance = agentInstance == null ? null : agentInstance.trim();
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId == null ? null : scriptId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId == null ? null : clusterId.trim();
    }

    public String getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(String schedulingId) {
        this.schedulingId = schedulingId == null ? null : schedulingId.trim();
    }

    public String getSchedulingInstanceId() {
        return schedulingInstanceId;
    }

    public void setSchedulingInstanceId(String schedulingInstanceId) {
        this.schedulingInstanceId = schedulingInstanceId == null ? null : schedulingInstanceId.trim();
    }

    public String getSchedulingNodeId() {
        return schedulingNodeId;
    }

    public void setSchedulingNodeId(String schedulingNodeId) {
        this.schedulingNodeId = schedulingNodeId == null ? null : schedulingNodeId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getJobFinalStatus() {
        return jobFinalStatus;
    }

    public void setJobFinalStatus(String jobFinalStatus) {
        this.jobFinalStatus = jobFinalStatus == null ? null : jobFinalStatus.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}