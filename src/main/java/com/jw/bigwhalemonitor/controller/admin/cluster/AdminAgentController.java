package com.jw.bigwhalemonitor.controller.admin.cluster;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoAgent;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.boot.cfgxml.internal.CfgXmlAccessServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/cluster/agent")
public class AdminAgentController extends BaseController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ScriptService scriptService;

    @PostMapping("/save.api")
    public Msg save(@RequestBody DtoAgent dtoAgent) {
        if (dtoAgent.validate() != null) {
            return failed(dtoAgent.validate());
        }
        if (dtoAgent.getId() == null) {
            // 这是新增的代理，先判断name是否重复
            List<Agent> byName = agentService.getByName(dtoAgent.getName());
            if (byName.size() > 0) {
                return failed("新增的代理name是重复的，代理name需要是唯一的");
            }
        } else {
            // 如果是更新，拿到更新对应的agent
            Agent agentById = agentService.getById(dtoAgent.getId());
            if (agentById == null) {
                return failed("要更改的agent不存在");
            }
            // 如果更新的内容是clusterId
            if (StringUtils.isNotBlank(agentById.getClusterId()) && !dtoAgent.getClusterId().equals(agentById.getClusterId())) {
                // 判断同clusterId下是否还有其他的agent,id不同
                List<Agent> diff = agentService.getByClusterId(agentById.getClusterId(), agentById.getId());
                if (diff.isEmpty()) {
                    // 其他代理中不存在相同clusterId的
                    // 查看被修改的代理中的唯一的clusterID是否包含的有脚本
                    List<Script> byClusterId = scriptService.getByClusterId(agentById.getClusterId());
                    if (!byClusterId.isEmpty()) {
                        // 该集群中存在脚本
                        return failed("该集群中存在脚本");
                    }
                }
            }
        }
        Agent agent = new Agent();
        BeanUtils.copyProperties(dtoAgent, agent);
        agent.setInstances(StringUtils.join(dtoAgent.getInstances(), ","));
        agentService.save(agent);
        return success("新增集群代理success");
    }

    @PostMapping("/delete.api")
    public Msg deleteById(@RequestParam String id) {
        // 删除前先检查脚本中是否有该代理的脚本，并且需要进行解绑
        Agent agent = agentService.getById(id);
        if (agent == null) {
            return failed();
        }
        if (StringUtils.isNotBlank(agent.getClusterId())) {
            return failed("代理需要先与集群解除关联");
        }
        List<Script> byAgentId = scriptService.getByAgentId(id);
        if (byAgentId.size() > 0) {
            return failed("脚本中还有该代理用户");
        }
        agentService.deleteById(id);
        return success("删除代理用户success.");
    }

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoAgent dtoAgent) {
        PageInfo<Agent> all = agentService.getAll(dtoAgent);
        List<Agent> list = all.getList();
        List<DtoAgent> content = new ArrayList<>();
        for (Agent agent : list) {
            DtoAgent da = new DtoAgent();
            BeanUtils.copyProperties(agent, da);
            content.add(da);
        }
        PageImpl p = new PageImpl(content);
        return success(p);
    }

}


