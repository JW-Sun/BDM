package com.jw.bigwhalemonitor.dto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jw.bigwhalemonitor.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoScheduling extends AbstractPageDto {

    private String id;
    private String uid;
    private Integer type;
    private List<String> scriptIds;
    /**
     * 周期
     */
    private Integer cycle;
    private Integer intervals;
    private Integer minute;
    private Integer hour;
    private List<String> week;
    /**
     * cron表达式
     */
    private String cron;
    private Date startTime;
    private Date endTime;

    private String topology;
    private Boolean repeatSubmit;
    private Date lastExecuteTime;

    private Boolean exRestart;
    private Integer waitingBatches;
    private Boolean blockingRestart;

    private Boolean sendEmail;
    private List<String> dingdingHooks;
    private Date createTime;
    private Date updateTime;
    private Boolean enabled;

    /**
     * 搜索字段
     */
    private String scriptId;
    /**
     * 前端列表树形展示
     */
    private List<Map<String, Object>> nodeTree;

    @Deprecated
    private List<String> subScriptIds;

    @Override
    public String validate() {
        if (CollectionUtils.isEmpty(scriptIds)) {
            return "脚本不能为空";
        }
        if (type == Constant.SCHEDULING_TYPE_BATCH && StringUtils.isBlank(topology)) {
            return "拓扑不能为空";
        }
        if (startTime == null || endTime == null) {
            return "请选择时间范围";
        }
        if (cron == null) {
            if (this.cycle == Constant.TIMER_CYCLE_MINUTE && intervals == null) {
                return "时间间隔不能为空";
            }
            if (this.cycle == Constant.TIMER_CYCLE_HOUR && this.minute == null) {
                return "分钟不能为空";
            }
            if (this.cycle == Constant.TIMER_CYCLE_DAY && (this.hour == null || this.minute == null)) {
                return "小时、分钟不能为空";
            }
            if (this.cycle == Constant.TIMER_CYCLE_WEEK && (this.week == null || this.hour == null || this.minute == null)) {
                return "周、小时、分钟不能为空";
            }
        }
        return null;
    }

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

    /***
     *  方法解析写一个节点
     * @param currentNodeId
     * @return
     */
    public Map<String, String> analyzeNextNode(String currentNodeId) {
        Map<String, String> nodeIdToScriptId = new HashMap<>();
        // 将scriptsId的List进行合并
        StringBuilder bd = new StringBuilder();
        for (String s : scriptIds) {
            bd.append(s).append(",");
        }
        bd.deleteCharAt(bd.length() - 1);

        // 在计算类型是流类型的时候将值都设置位合并之后的scriptsId
        if (type == Constant.SCHEDULING_TYPE_STREAMING) {
            if (currentNodeId == null) {
                nodeIdToScriptId.put(bd.toString(), bd.toString());
            }
            return nodeIdToScriptId;
        }

        // 批处理要解析topology这个长字符串进行解决  👇拓扑结构
        JSONObject jsonObject = JSON.parseObject(topology);
        JSONArray nodes = jsonObject.getJSONArray("nodes");
        JSONArray lines = jsonObject.getJSONArray("lines");

        // 解析nodes将其中的(id, data)这样的数据形势加入到nodeIdToScriptId中
        nodes.forEach(node -> nodeIdToScriptId.put(((JSONObject)node).getString("id"), ((JSONObject)node).getString("data")));

        // 解析lines，里面包含的结构应该是一个{"to":{"id":"xxxxxxx"}} 领接表的感觉，将to的下一个位置加入到toIds中
        List<String> toIds = new ArrayList<>();
        lines.forEach(line -> toIds.add(((JSONObject)line).getJSONObject("to").getString("id")));

        // 标记根节点Id,找到不在领接表中的节点就是对应的根节点
        String rootNodeId = null;
        for (String id : nodeIdToScriptId.keySet()) {
            if (!toIds.contains(id)) {
                rootNodeId = id;
                break;
            }
        }

        // 当前nodeId是空就直接返回Map(根节点Id，rootNodeId对应的scriptId)
        if (currentNodeId == null) {
            return Collections.singletonMap(rootNodeId, nodeIdToScriptId.get(rootNodeId));
        } else {
            nodeIdToScriptId.remove(rootNodeId);
            for (int i = 0; i < lines.size(); i ++) {
                JSONObject line = lines.getJSONObject(i);
                String fromId = line.getJSONObject("from").getString("id");
                // 只保留当前节点 == from节点Id 的nodeToScript节点的后继节点的映射
                if (!fromId.equals(currentNodeId)) {
                    nodeIdToScriptId.remove(line.getJSONObject("to").getString("id"));
                }
            }
            return nodeIdToScriptId;
        }
    }

}
