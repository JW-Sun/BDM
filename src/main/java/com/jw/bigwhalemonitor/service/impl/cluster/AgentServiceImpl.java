package com.jw.bigwhalemonitor.service.impl.cluster;

import ch.ethz.ssh2.Connection;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.config.SshConfig;
import com.jw.bigwhalemonitor.dto.DtoAgent;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.AgentExample;
import com.jw.bigwhalemonitor.mapper.AgentMapper;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AgentServiceImpl implements AgentService {

    private Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private SshConfig sshConfig;


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

    /***
     *  通过clusterId找到该集群的所有代理，获得代理中的instance列表，根据是否进行校验来返回可用实例
     * @param clusterId
     * @param b
     * @return
     */
    @Override
    public String getInstanceByClusterId(String clusterId, boolean b) {
        // 查找clusterId对应的agent代理
        List<Agent> byClusterId = getByClusterId(clusterId);
        // 遍历每一个代理
        while (!byClusterId.isEmpty()) {
            int random = new Random().nextInt(byClusterId.size());
            Agent agent = byClusterId.get(random);
            try {
                return getInstanceByAgentId(agent.getId(), b);
            } catch (Exception e) {
                byClusterId.remove(agent);
            }
        }
        throw new IllegalStateException("No agent instance accessible");
    }

    public String getInstanceByAgentId(String id, boolean b) {
        // 通过agentID进行查找
        Agent agent = getById(id);
        List<String> instances = new ArrayList<>();
        Collections.addAll(instances, agent.getInstances().split(","));
        while (!instances.isEmpty()) {
            int random = new Random().nextInt(instances.size());
            String instance = instances.get(random);
            // 是否检验是否可行
            if (b) {
                if (isAccessible(instance)) {
                    return instance;
                } else {
                    instances.remove(instance);
                }
            } else {
                // 不需要检验直接返回正确结果因此就不抛出异常了
                return instance;
            }
        }
        throw new IllegalStateException("No agent instance accessible");
    }

    private boolean isAccessible(String instance) {
        // 校验是否可以登录实例服务器
        Connection conn = null;
        try {
            // 判断实例的地址是什么样的
            if (instance.contains(":")) {
                String[] split = instance.split(":");
                conn = new Connection(split[0], Integer.parseInt(split[1]));
            } else {
                conn = new Connection(instance);
            }
            conn.connect(null, sshConfig.getConnectTimeout(), 30000);
            boolean authenticateWithPassword = conn.authenticateWithPassword(sshConfig.getUser(), sshConfig.getPassword());
            if (!authenticateWithPassword) {
                throw new IllegalArgumentException("Incorrect username or password");
            }
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }
}
