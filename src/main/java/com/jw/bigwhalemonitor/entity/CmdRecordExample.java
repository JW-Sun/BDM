package com.jw.bigwhalemonitor.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CmdRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CmdRecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(String value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(String value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(String value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(String value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(String value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLike(String value) {
            addCriterion("parent_id like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotLike(String value) {
            addCriterion("parent_id not like", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<String> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<String> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(String value1, String value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(String value1, String value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("agent_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("agent_id =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("agent_id <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("agent_id >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("agent_id >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("agent_id <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("agent_id <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("agent_id like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("agent_id not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("agent_id in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("agent_id not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("agent_id between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("agent_id not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceIsNull() {
            addCriterion("agent_instance is null");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceIsNotNull() {
            addCriterion("agent_instance is not null");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceEqualTo(String value) {
            addCriterion("agent_instance =", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceNotEqualTo(String value) {
            addCriterion("agent_instance <>", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceGreaterThan(String value) {
            addCriterion("agent_instance >", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceGreaterThanOrEqualTo(String value) {
            addCriterion("agent_instance >=", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceLessThan(String value) {
            addCriterion("agent_instance <", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceLessThanOrEqualTo(String value) {
            addCriterion("agent_instance <=", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceLike(String value) {
            addCriterion("agent_instance like", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceNotLike(String value) {
            addCriterion("agent_instance not like", value, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceIn(List<String> values) {
            addCriterion("agent_instance in", values, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceNotIn(List<String> values) {
            addCriterion("agent_instance not in", values, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceBetween(String value1, String value2) {
            addCriterion("agent_instance between", value1, value2, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andAgentInstanceNotBetween(String value1, String value2) {
            addCriterion("agent_instance not between", value1, value2, "agentInstance");
            return (Criteria) this;
        }

        public Criteria andScriptIdIsNull() {
            addCriterion("script_id is null");
            return (Criteria) this;
        }

        public Criteria andScriptIdIsNotNull() {
            addCriterion("script_id is not null");
            return (Criteria) this;
        }

        public Criteria andScriptIdEqualTo(String value) {
            addCriterion("script_id =", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdNotEqualTo(String value) {
            addCriterion("script_id <>", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdGreaterThan(String value) {
            addCriterion("script_id >", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdGreaterThanOrEqualTo(String value) {
            addCriterion("script_id >=", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdLessThan(String value) {
            addCriterion("script_id <", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdLessThanOrEqualTo(String value) {
            addCriterion("script_id <=", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdLike(String value) {
            addCriterion("script_id like", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdNotLike(String value) {
            addCriterion("script_id not like", value, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdIn(List<String> values) {
            addCriterion("script_id in", values, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdNotIn(List<String> values) {
            addCriterion("script_id not in", values, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdBetween(String value1, String value2) {
            addCriterion("script_id between", value1, value2, "scriptId");
            return (Criteria) this;
        }

        public Criteria andScriptIdNotBetween(String value1, String value2) {
            addCriterion("script_id not between", value1, value2, "scriptId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNull() {
            addCriterion("timeout is null");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNotNull() {
            addCriterion("timeout is not null");
            return (Criteria) this;
        }

        public Criteria andTimeoutEqualTo(Integer value) {
            addCriterion("timeout =", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotEqualTo(Integer value) {
            addCriterion("timeout <>", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThan(Integer value) {
            addCriterion("timeout >", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThanOrEqualTo(Integer value) {
            addCriterion("timeout >=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThan(Integer value) {
            addCriterion("timeout <", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThanOrEqualTo(Integer value) {
            addCriterion("timeout <=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutIn(List<Integer> values) {
            addCriterion("timeout in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotIn(List<Integer> values) {
            addCriterion("timeout not in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutBetween(Integer value1, Integer value2) {
            addCriterion("timeout between", value1, value2, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotBetween(Integer value1, Integer value2) {
            addCriterion("timeout not between", value1, value2, "timeout");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andClusterIdIsNull() {
            addCriterion("cluster_id is null");
            return (Criteria) this;
        }

        public Criteria andClusterIdIsNotNull() {
            addCriterion("cluster_id is not null");
            return (Criteria) this;
        }

        public Criteria andClusterIdEqualTo(String value) {
            addCriterion("cluster_id =", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotEqualTo(String value) {
            addCriterion("cluster_id <>", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdGreaterThan(String value) {
            addCriterion("cluster_id >", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_id >=", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdLessThan(String value) {
            addCriterion("cluster_id <", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdLessThanOrEqualTo(String value) {
            addCriterion("cluster_id <=", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdLike(String value) {
            addCriterion("cluster_id like", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotLike(String value) {
            addCriterion("cluster_id not like", value, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdIn(List<String> values) {
            addCriterion("cluster_id in", values, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotIn(List<String> values) {
            addCriterion("cluster_id not in", values, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdBetween(String value1, String value2) {
            addCriterion("cluster_id between", value1, value2, "clusterId");
            return (Criteria) this;
        }

        public Criteria andClusterIdNotBetween(String value1, String value2) {
            addCriterion("cluster_id not between", value1, value2, "clusterId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdIsNull() {
            addCriterion("scheduling_id is null");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdIsNotNull() {
            addCriterion("scheduling_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdEqualTo(String value) {
            addCriterion("scheduling_id =", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdNotEqualTo(String value) {
            addCriterion("scheduling_id <>", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdGreaterThan(String value) {
            addCriterion("scheduling_id >", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdGreaterThanOrEqualTo(String value) {
            addCriterion("scheduling_id >=", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdLessThan(String value) {
            addCriterion("scheduling_id <", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdLessThanOrEqualTo(String value) {
            addCriterion("scheduling_id <=", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdLike(String value) {
            addCriterion("scheduling_id like", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdNotLike(String value) {
            addCriterion("scheduling_id not like", value, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdIn(List<String> values) {
            addCriterion("scheduling_id in", values, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdNotIn(List<String> values) {
            addCriterion("scheduling_id not in", values, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdBetween(String value1, String value2) {
            addCriterion("scheduling_id between", value1, value2, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingIdNotBetween(String value1, String value2) {
            addCriterion("scheduling_id not between", value1, value2, "schedulingId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdIsNull() {
            addCriterion("scheduling_instance_id is null");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdIsNotNull() {
            addCriterion("scheduling_instance_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdEqualTo(String value) {
            addCriterion("scheduling_instance_id =", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdNotEqualTo(String value) {
            addCriterion("scheduling_instance_id <>", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdGreaterThan(String value) {
            addCriterion("scheduling_instance_id >", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdGreaterThanOrEqualTo(String value) {
            addCriterion("scheduling_instance_id >=", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdLessThan(String value) {
            addCriterion("scheduling_instance_id <", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdLessThanOrEqualTo(String value) {
            addCriterion("scheduling_instance_id <=", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdLike(String value) {
            addCriterion("scheduling_instance_id like", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdNotLike(String value) {
            addCriterion("scheduling_instance_id not like", value, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdIn(List<String> values) {
            addCriterion("scheduling_instance_id in", values, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdNotIn(List<String> values) {
            addCriterion("scheduling_instance_id not in", values, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdBetween(String value1, String value2) {
            addCriterion("scheduling_instance_id between", value1, value2, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingInstanceIdNotBetween(String value1, String value2) {
            addCriterion("scheduling_instance_id not between", value1, value2, "schedulingInstanceId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdIsNull() {
            addCriterion("scheduling_node_id is null");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdIsNotNull() {
            addCriterion("scheduling_node_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdEqualTo(String value) {
            addCriterion("scheduling_node_id =", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdNotEqualTo(String value) {
            addCriterion("scheduling_node_id <>", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdGreaterThan(String value) {
            addCriterion("scheduling_node_id >", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("scheduling_node_id >=", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdLessThan(String value) {
            addCriterion("scheduling_node_id <", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdLessThanOrEqualTo(String value) {
            addCriterion("scheduling_node_id <=", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdLike(String value) {
            addCriterion("scheduling_node_id like", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdNotLike(String value) {
            addCriterion("scheduling_node_id not like", value, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdIn(List<String> values) {
            addCriterion("scheduling_node_id in", values, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdNotIn(List<String> values) {
            addCriterion("scheduling_node_id not in", values, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdBetween(String value1, String value2) {
            addCriterion("scheduling_node_id between", value1, value2, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andSchedulingNodeIdNotBetween(String value1, String value2) {
            addCriterion("scheduling_node_id not between", value1, value2, "schedulingNodeId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andJobIdIsNull() {
            addCriterion("job_id is null");
            return (Criteria) this;
        }

        public Criteria andJobIdIsNotNull() {
            addCriterion("job_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobIdEqualTo(String value) {
            addCriterion("job_id =", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotEqualTo(String value) {
            addCriterion("job_id <>", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThan(String value) {
            addCriterion("job_id >", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThanOrEqualTo(String value) {
            addCriterion("job_id >=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThan(String value) {
            addCriterion("job_id <", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThanOrEqualTo(String value) {
            addCriterion("job_id <=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLike(String value) {
            addCriterion("job_id like", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotLike(String value) {
            addCriterion("job_id not like", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdIn(List<String> values) {
            addCriterion("job_id in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotIn(List<String> values) {
            addCriterion("job_id not in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdBetween(String value1, String value2) {
            addCriterion("job_id between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotBetween(String value1, String value2) {
            addCriterion("job_id not between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusIsNull() {
            addCriterion("job_final_status is null");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusIsNotNull() {
            addCriterion("job_final_status is not null");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusEqualTo(String value) {
            addCriterion("job_final_status =", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusNotEqualTo(String value) {
            addCriterion("job_final_status <>", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusGreaterThan(String value) {
            addCriterion("job_final_status >", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusGreaterThanOrEqualTo(String value) {
            addCriterion("job_final_status >=", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusLessThan(String value) {
            addCriterion("job_final_status <", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusLessThanOrEqualTo(String value) {
            addCriterion("job_final_status <=", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusLike(String value) {
            addCriterion("job_final_status like", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusNotLike(String value) {
            addCriterion("job_final_status not like", value, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusIn(List<String> values) {
            addCriterion("job_final_status in", values, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusNotIn(List<String> values) {
            addCriterion("job_final_status not in", values, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusBetween(String value1, String value2) {
            addCriterion("job_final_status between", value1, value2, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andJobFinalStatusNotBetween(String value1, String value2) {
            addCriterion("job_final_status not between", value1, value2, "jobFinalStatus");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}