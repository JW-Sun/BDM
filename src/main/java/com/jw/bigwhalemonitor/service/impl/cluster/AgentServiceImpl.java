package com.jw.bigwhalemonitor.service.impl.cluster;

import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.mapper.AgentMapper;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentMapper agentMapper;


    @Override
    public List<Agent> getAll() {
        List<Agent> agents = agentMapper.selectByExample(null);
        return agents;
    }
}
