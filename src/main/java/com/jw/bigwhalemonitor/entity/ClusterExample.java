package com.jw.bigwhalemonitor.entity;

import java.util.ArrayList;
import java.util.List;

public class ClusterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClusterExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andYarnUrlIsNull() {
            addCriterion("yarn_url is null");
            return (Criteria) this;
        }

        public Criteria andYarnUrlIsNotNull() {
            addCriterion("yarn_url is not null");
            return (Criteria) this;
        }

        public Criteria andYarnUrlEqualTo(String value) {
            addCriterion("yarn_url =", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlNotEqualTo(String value) {
            addCriterion("yarn_url <>", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlGreaterThan(String value) {
            addCriterion("yarn_url >", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlGreaterThanOrEqualTo(String value) {
            addCriterion("yarn_url >=", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlLessThan(String value) {
            addCriterion("yarn_url <", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlLessThanOrEqualTo(String value) {
            addCriterion("yarn_url <=", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlLike(String value) {
            addCriterion("yarn_url like", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlNotLike(String value) {
            addCriterion("yarn_url not like", value, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlIn(List<String> values) {
            addCriterion("yarn_url in", values, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlNotIn(List<String> values) {
            addCriterion("yarn_url not in", values, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlBetween(String value1, String value2) {
            addCriterion("yarn_url between", value1, value2, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andYarnUrlNotBetween(String value1, String value2) {
            addCriterion("yarn_url not between", value1, value2, "yarnUrl");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterIsNull() {
            addCriterion("default_file_cluster is null");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterIsNotNull() {
            addCriterion("default_file_cluster is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterEqualTo(Boolean value) {
            addCriterion("default_file_cluster =", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterNotEqualTo(Boolean value) {
            addCriterion("default_file_cluster <>", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterGreaterThan(Boolean value) {
            addCriterion("default_file_cluster >", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("default_file_cluster >=", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterLessThan(Boolean value) {
            addCriterion("default_file_cluster <", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterLessThanOrEqualTo(Boolean value) {
            addCriterion("default_file_cluster <=", value, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterIn(List<Boolean> values) {
            addCriterion("default_file_cluster in", values, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterNotIn(List<Boolean> values) {
            addCriterion("default_file_cluster not in", values, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterBetween(Boolean value1, Boolean value2) {
            addCriterion("default_file_cluster between", value1, value2, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andDefaultFileClusterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("default_file_cluster not between", value1, value2, "defaultFileCluster");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledIsNull() {
            addCriterion("flink_proxy_user_enabled is null");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledIsNotNull() {
            addCriterion("flink_proxy_user_enabled is not null");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledEqualTo(Boolean value) {
            addCriterion("flink_proxy_user_enabled =", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledNotEqualTo(Boolean value) {
            addCriterion("flink_proxy_user_enabled <>", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledGreaterThan(Boolean value) {
            addCriterion("flink_proxy_user_enabled >", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("flink_proxy_user_enabled >=", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledLessThan(Boolean value) {
            addCriterion("flink_proxy_user_enabled <", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("flink_proxy_user_enabled <=", value, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledIn(List<Boolean> values) {
            addCriterion("flink_proxy_user_enabled in", values, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledNotIn(List<Boolean> values) {
            addCriterion("flink_proxy_user_enabled not in", values, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("flink_proxy_user_enabled between", value1, value2, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFlinkProxyUserEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("flink_proxy_user_enabled not between", value1, value2, "flinkProxyUserEnabled");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsIsNull() {
            addCriterion("fs_default_fs is null");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsIsNotNull() {
            addCriterion("fs_default_fs is not null");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsEqualTo(String value) {
            addCriterion("fs_default_fs =", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsNotEqualTo(String value) {
            addCriterion("fs_default_fs <>", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsGreaterThan(String value) {
            addCriterion("fs_default_fs >", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsGreaterThanOrEqualTo(String value) {
            addCriterion("fs_default_fs >=", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsLessThan(String value) {
            addCriterion("fs_default_fs <", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsLessThanOrEqualTo(String value) {
            addCriterion("fs_default_fs <=", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsLike(String value) {
            addCriterion("fs_default_fs like", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsNotLike(String value) {
            addCriterion("fs_default_fs not like", value, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsIn(List<String> values) {
            addCriterion("fs_default_fs in", values, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsNotIn(List<String> values) {
            addCriterion("fs_default_fs not in", values, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsBetween(String value1, String value2) {
            addCriterion("fs_default_fs between", value1, value2, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsDefaultFsNotBetween(String value1, String value2) {
            addCriterion("fs_default_fs not between", value1, value2, "fsDefaultFs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsIsNull() {
            addCriterion("fs_webhdfs is null");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsIsNotNull() {
            addCriterion("fs_webhdfs is not null");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsEqualTo(String value) {
            addCriterion("fs_webhdfs =", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsNotEqualTo(String value) {
            addCriterion("fs_webhdfs <>", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsGreaterThan(String value) {
            addCriterion("fs_webhdfs >", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsGreaterThanOrEqualTo(String value) {
            addCriterion("fs_webhdfs >=", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsLessThan(String value) {
            addCriterion("fs_webhdfs <", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsLessThanOrEqualTo(String value) {
            addCriterion("fs_webhdfs <=", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsLike(String value) {
            addCriterion("fs_webhdfs like", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsNotLike(String value) {
            addCriterion("fs_webhdfs not like", value, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsIn(List<String> values) {
            addCriterion("fs_webhdfs in", values, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsNotIn(List<String> values) {
            addCriterion("fs_webhdfs not in", values, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsBetween(String value1, String value2) {
            addCriterion("fs_webhdfs between", value1, value2, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsWebhdfsNotBetween(String value1, String value2) {
            addCriterion("fs_webhdfs not between", value1, value2, "fsWebhdfs");
            return (Criteria) this;
        }

        public Criteria andFsUserIsNull() {
            addCriterion("fs_user is null");
            return (Criteria) this;
        }

        public Criteria andFsUserIsNotNull() {
            addCriterion("fs_user is not null");
            return (Criteria) this;
        }

        public Criteria andFsUserEqualTo(String value) {
            addCriterion("fs_user =", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserNotEqualTo(String value) {
            addCriterion("fs_user <>", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserGreaterThan(String value) {
            addCriterion("fs_user >", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserGreaterThanOrEqualTo(String value) {
            addCriterion("fs_user >=", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserLessThan(String value) {
            addCriterion("fs_user <", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserLessThanOrEqualTo(String value) {
            addCriterion("fs_user <=", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserLike(String value) {
            addCriterion("fs_user like", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserNotLike(String value) {
            addCriterion("fs_user not like", value, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserIn(List<String> values) {
            addCriterion("fs_user in", values, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserNotIn(List<String> values) {
            addCriterion("fs_user not in", values, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserBetween(String value1, String value2) {
            addCriterion("fs_user between", value1, value2, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsUserNotBetween(String value1, String value2) {
            addCriterion("fs_user not between", value1, value2, "fsUser");
            return (Criteria) this;
        }

        public Criteria andFsDirIsNull() {
            addCriterion("fs_dir is null");
            return (Criteria) this;
        }

        public Criteria andFsDirIsNotNull() {
            addCriterion("fs_dir is not null");
            return (Criteria) this;
        }

        public Criteria andFsDirEqualTo(String value) {
            addCriterion("fs_dir =", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirNotEqualTo(String value) {
            addCriterion("fs_dir <>", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirGreaterThan(String value) {
            addCriterion("fs_dir >", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirGreaterThanOrEqualTo(String value) {
            addCriterion("fs_dir >=", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirLessThan(String value) {
            addCriterion("fs_dir <", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirLessThanOrEqualTo(String value) {
            addCriterion("fs_dir <=", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirLike(String value) {
            addCriterion("fs_dir like", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirNotLike(String value) {
            addCriterion("fs_dir not like", value, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirIn(List<String> values) {
            addCriterion("fs_dir in", values, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirNotIn(List<String> values) {
            addCriterion("fs_dir not in", values, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirBetween(String value1, String value2) {
            addCriterion("fs_dir between", value1, value2, "fsDir");
            return (Criteria) this;
        }

        public Criteria andFsDirNotBetween(String value1, String value2) {
            addCriterion("fs_dir not between", value1, value2, "fsDir");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListIsNull() {
            addCriterion("streaming_black_node_list is null");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListIsNotNull() {
            addCriterion("streaming_black_node_list is not null");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListEqualTo(String value) {
            addCriterion("streaming_black_node_list =", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListNotEqualTo(String value) {
            addCriterion("streaming_black_node_list <>", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListGreaterThan(String value) {
            addCriterion("streaming_black_node_list >", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListGreaterThanOrEqualTo(String value) {
            addCriterion("streaming_black_node_list >=", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListLessThan(String value) {
            addCriterion("streaming_black_node_list <", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListLessThanOrEqualTo(String value) {
            addCriterion("streaming_black_node_list <=", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListLike(String value) {
            addCriterion("streaming_black_node_list like", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListNotLike(String value) {
            addCriterion("streaming_black_node_list not like", value, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListIn(List<String> values) {
            addCriterion("streaming_black_node_list in", values, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListNotIn(List<String> values) {
            addCriterion("streaming_black_node_list not in", values, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListBetween(String value1, String value2) {
            addCriterion("streaming_black_node_list between", value1, value2, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andStreamingBlackNodeListNotBetween(String value1, String value2) {
            addCriterion("streaming_black_node_list not between", value1, value2, "streamingBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListIsNull() {
            addCriterion("batch_black_node_list is null");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListIsNotNull() {
            addCriterion("batch_black_node_list is not null");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListEqualTo(String value) {
            addCriterion("batch_black_node_list =", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListNotEqualTo(String value) {
            addCriterion("batch_black_node_list <>", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListGreaterThan(String value) {
            addCriterion("batch_black_node_list >", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListGreaterThanOrEqualTo(String value) {
            addCriterion("batch_black_node_list >=", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListLessThan(String value) {
            addCriterion("batch_black_node_list <", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListLessThanOrEqualTo(String value) {
            addCriterion("batch_black_node_list <=", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListLike(String value) {
            addCriterion("batch_black_node_list like", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListNotLike(String value) {
            addCriterion("batch_black_node_list not like", value, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListIn(List<String> values) {
            addCriterion("batch_black_node_list in", values, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListNotIn(List<String> values) {
            addCriterion("batch_black_node_list not in", values, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListBetween(String value1, String value2) {
            addCriterion("batch_black_node_list between", value1, value2, "batchBlackNodeList");
            return (Criteria) this;
        }

        public Criteria andBatchBlackNodeListNotBetween(String value1, String value2) {
            addCriterion("batch_black_node_list not between", value1, value2, "batchBlackNodeList");
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