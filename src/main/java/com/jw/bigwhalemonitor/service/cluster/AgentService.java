package com.jw.bigwhalemonitor.service.cluster;

import com.jw.bigwhalemonitor.entity.Agent;

import java.util.List;

public interface AgentService {

    public List<Agent> getAll();

    List<Agent> getByClusterId(String id);
}
