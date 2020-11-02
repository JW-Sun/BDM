package com.jw.bigwhalemonitor.service.impl.cluster;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoAgent;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.AgentExample;
import com.jw.bigwhalemonitor.mapper.AgentMapper;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentMapper agentMapper;


    @Override
    public List<Agent> getAll() {
        List<Agent> agents = agentMapper.selectByExample(null);
        return agents;
    }

    @Override
    public List<Agent> getByClusterId(String id) {
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(id);
        List<Agent> agents = agentMapper.selectByExample(example);
        return agents;
    }

    @Override
    public List<Agent> getByName(String name) {
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Agent> agents = agentMapper.selectByExample(example);
        return agents;
    }

    @Override
    public Agent getById(String id) {
        return agentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Agent> getByClusterId(String clusterId, String id) {
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(clusterId);
        criteria.andIdNotEqualTo(id);
        List<Agent> agents = agentMapper.selectByExample(example);
        return agents;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Agent agent) {
        if (agent.getId() == null) {
            agent.setId(UUID.randomUUID().toString());
            agentMapper.insert(agent);
        } else {
            agentMapper.updateByPrimaryKey(agent);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        agentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Agent> getAll(DtoAgent dtoAgent) {
        AgentExample example = new AgentExample();
        AgentExample.Criteria criteria = example.createCriteria();
        if (dtoAgent.getName() != null) {
            criteria.andNameEqualTo(dtoAgent.getName());
        }
        if (dtoAgent.getClusterId() != null) {
            criteria.andClusterIdEqualTo(dtoAgent.getClusterId());
        }
        PageHelper.startPage(dtoAgent.pageNo - 1, dtoAgent.pageSize);
        List<Agent> agents = agentMapper.selectByExample(example);
        PageInfo<Agent> pageInfo = new PageInfo<>(agents);
        return pageInfo;
    }
}
