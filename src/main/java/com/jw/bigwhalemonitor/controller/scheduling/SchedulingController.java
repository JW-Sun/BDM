package com.jw.bigwhalemonitor.controller.scheduling;


import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoScheduling;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.SchedulingService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController extends BaseController {

    private Map<Integer, String> scriptIconClass = new HashMap<>();

    @Autowired
    private SchedulingService schedulingService;
    @Autowired
    private ScriptService scriptService;

    public SchedulingController() {
        scriptIconClass.put(0, "icon-shell");
        scriptIconClass.put(1, "icon-spark");
        scriptIconClass.put(2, "icon-spark");
        scriptIconClass.put(3, "icon-flink");
        scriptIconClass.put(4, "icon-flink");
    }

    /***
     *  schedule获得page信息
     * @param req
     * @return
     */
    @RequestMapping(value = "/getpage.api", method = RequestMethod.POST)
    public Msg getPage(@RequestBody DtoScheduling req) {
        LoginUser user = getCurrentUser();
        if (!user.isRoot()) {
            req.setUid(user.getId());
        }

        // 将里面先查到的scriptID，week和dingdinghooks按照要求进行拆分并以List形式插入
        PageInfo<DtoScheduling> dtoSchedulingByPageQuery = schedulingService.getByPageQuery(req);
        List<DtoScheduling> list = dtoSchedulingByPageQuery.getList();
        // 根据DtoScheduling构造节点树
        for (DtoScheduling dtoScheduling : list) {
            Map<String, Object> nodeTree = new HashMap<>();
            /* 构造dfs拓补节点关系的图 ？？？ */
            generateNodeTree(dtoScheduling, nodeTree, null);
            dtoScheduling.setNodeTree(Collections.singletonList(nodeTree));
        }
        Page page = new PageImpl(list);
        return success(page);
    }


    @RequestMapping(value = "/save.api", method = RequestMethod.POST)
    public Msg saveOrUpdate(@RequestBody DtoScheduling req) throws SchedulerException {
        String msg = req.validate();
        if (msg != null) {
            return failed(msg);
        }
        LoginUser user = getCurrentUser();
        if (!user.isRoot()) {
            req.setUid(user.getId());
        }
        Date now = new Date();
        if (req.getId() == null) {
            req.setCreateTime(now);
        } else {
            Scheduling dbScheduling = schedulingService.getById(req.getId());
            if (dbScheduling == null) {
                return failed();
            }
            if (dbScheduling.getType() == Constant.SCHEDULING_TYPE_BATCH) {
                SchedulerUtils.deleteScheduler(dbScheduling.getId(), Constant.JobGroup.TIMED);
            } else {
                SchedulerUtils.deleteScheduler(dbScheduling.getId(), Constant.JobGroup.MONITOR);
            }
        }
        req.setUpdateTime(now);
        req.setLastExecuteTime(null);
        Scheduling scheduling = new Scheduling();
        BeanUtils.copyProperties(req, scheduling);
        scheduling.setScriptIds(StringUtils.join(req.getScriptIds(), ","));
        if (req.getWeek() != null && !req.getWeek().isEmpty()) {
            scheduling.setWeek(StringUtils.join(req.getWeek(), ","));
        }
        if (req.getDingdingHooks() != null && !req.getDingdingHooks().isEmpty()) {
            scheduling.setDingdingHooks(StringUtils.join(req.getDingdingHooks(), ","));
        }
        scheduling = schedulingService.save(scheduling);

        /* 如果这个调度是成立的，那么直接调用调用对应的应用进行调度 */
        // TODO 调用对应的调度操作
        // if (scheduling.getEnabled()) {
        //     if (scheduling.getType() == Constant.SCHEDULING_TYPE_BATCH) {
        //         TimedTask.build(scheduling);
        //     } else {
        //         AbstractMonitorRunner.build(scheduling);
        //     }
        // }
        if (req.getId() == null) {
            req.setId(scheduling.getId());
        }
        return success(req);
    }

    @RequestMapping(value = "/delete.api", method = RequestMethod.POST)
    public Msg delete(@RequestParam String id) throws SchedulerException {
        Scheduling scheduling = schedulingService.getById(id);
        if (scheduling != null) {
            if (scheduling.getType() == Constant.SCHEDULING_TYPE_BATCH) {
                SchedulerUtils.deleteScheduler(scheduling.getId(), Constant.JobGroup.TIMED);
            } else {
                SchedulerUtils.deleteScheduler(scheduling.getId(), Constant.JobGroup.MONITOR);
            }
            schedulingService.deleteById(id);
        }
        return success();
    }

    /***
     *  =================构造节点树================
     * @param dtoScheduling
     * @param node
     * @param currentNodeId
     */
    private void generateNodeTree(DtoScheduling dtoScheduling, Map<String, Object> node, String currentNodeId) {
        // 获得scriptId与后继节点之间的关系  流类型直接返回（scriptIds，scriptIds）
        Map<String, String> nodeIdToScriptId = dtoScheduling.analyzeNextNode(currentNodeId);
        for (Map.Entry<String, String> entry : nodeIdToScriptId.entrySet()) {
            String nodeId = entry.getKey();
            String scriptId = entry.getValue();
            Script scriptById = scriptService.getById(scriptId);
            // 当前的节点是null的，将节点的信息加入node的结构中，此处就是根节点
            if (currentNodeId == null) {
                node.put("text", scriptById.getName());
                node.put("data", nodeId); // 这个nodeId可能直接返回的是scriptIds
                node.put("icon", "iconfont " + scriptIconClass.get(dtoScheduling.getType()));
                // dfs
                generateNodeTree(dtoScheduling, node, nodeId);
            } else {
                // 当前存在节点，需要构造孩子节点
                Map<String, Object> childNode = new HashMap<>();
                childNode.put("text", scriptById.getName());
                childNode.put("data", nodeId);
                childNode.put("icon", "iconfont " + scriptIconClass.get(scriptById.getType()));
                // 这个结构保存后继节点的信息，一个节点代表Map
                List<Map<String, Object>> nextNodeList = (List<Map<String, Object>>) node.get("nodes");
                if (nextNodeList == null) {
                    nextNodeList = new ArrayList<>();
                    node.put("nodes", nextNodeList);
                }
                ((List<Map<String, Object>>) node.get("nodes")).add(childNode);

                // dfs
                generateNodeTree(dtoScheduling, childNode, nodeId);
            }
        }
    }
}
