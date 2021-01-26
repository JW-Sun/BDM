package com.jw.bigwhalemonitor.service.cluster;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoAgent;
import com.jw.bigwhalemonitor.entity.Agent;

import java.util.List;

public interface AgentService {

    public List<Agent> getAll();

    List<Agent> getByClusterId(String id);

    List<Agent> getByName(String name);

    Agent getById(String id);

    List<Agent> getByClusterId(String clusterId, String id);

    void save(Agent agent);

    void deleteById(String id);

    PageInfo<Agent> getAll(DtoAgent dtoAgent);

    String getInstanceByClusterId(String clusterId, boolean b);

    String getInstanceByAgentId(String agentId, boolean b);
}
