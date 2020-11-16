package com.jw.bigwhalemonitor.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jw.bigwhalemonitor.common.Constant;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String generateCron() {
        if (StringUtils.isNotBlank(cron)) {
            return cron;
        } else {
            String cron = null;
            if (cycle == Constant.TIMER_CYCLE_MINUTE) {
                cron = "0 */" + intervals + " * * * ? *";
            } else if (cycle == Constant.TIMER_CYCLE_HOUR) {
                cron = "0 " + minute + " * * * ? *";
            } else if (cycle == Constant.TIMER_CYCLE_DAY) {
                cron = "0 " + minute + " " + hour + " * * ? *";
            } else if (cycle == Constant.TIMER_CYCLE_WEEK) {
                cron = "0 " + minute + " " + hour + " ? * " + week + " *";
            }
            if (cron == null) {
                throw new IllegalArgumentException("cron expression is incorrect");
            }
            return cron;
        }
    }

    public Map<String, String> analyzeNextNode(String currentNodeId) {
        Map<String, String> nodeIdToScriptId = new HashMap<>();
        if (type == Constant.SCHEDULING_TYPE_STREAMING) {
            if (currentNodeId == null) {
                nodeIdToScriptId.put(scriptIds, scriptIds);
            }
            return nodeIdToScriptId;
        }
        JSONObject jsonObject = JSON.parseObject(topology);
        JSONArray nodes = jsonObject.getJSONArray("nodes");
        JSONArray lines = jsonObject.getJSONArray("lines");
        nodes.forEach(node -> nodeIdToScriptId.put(((JSONObject)node).getString("id"), ((JSONObject)node).getString("data")));
        List<String> toIds = new ArrayList<>();
        lines.forEach(line -> toIds.add(((JSONObject)line).getJSONObject("to").getString("id")));
        String rootNodeId = null;
        for (String id : nodeIdToScriptId.keySet()) {
            if (!toIds.contains(id)) {
                rootNodeId = id;
                break;
            }
        }
        if (currentNodeId == null) {
            return Collections.singletonMap(rootNodeId, nodeIdToScriptId.get(rootNodeId));
        } else {
            nodeIdToScriptId.remove(rootNodeId);
            for (int i = 0; i < lines.size(); i ++) {
                JSONObject line = lines.getJSONObject(i);
                String fromId = line.getJSONObject("from").getString("id");
                if (!fromId.equals(currentNodeId)) {
                    nodeIdToScriptId.remove(line.getJSONObject("to").getString("id"));
                }
            }
            return nodeIdToScriptId;
        }
    }

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