package com.jw.bigwhalemonitor.entity;

public class Cluster {
    private String id;

    private String name;

    private String yarnUrl;

    private Boolean defaultFileCluster;

    private Boolean flinkProxyUserEnabled;

    private String fsDefaultFs;

    private String fsWebhdfs;

    private String fsUser;

    private String fsDir;

    private String streamingBlackNodeList;

    private String batchBlackNodeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getYarnUrl() {
        return yarnUrl;
    }

    public void setYarnUrl(String yarnUrl) {
        this.yarnUrl = yarnUrl == null ? null : yarnUrl.trim();
    }

    public Boolean getDefaultFileCluster() {
        return defaultFileCluster;
    }

    public void setDefaultFileCluster(Boolean defaultFileCluster) {
        this.defaultFileCluster = defaultFileCluster;
    }

    public Boolean getFlinkProxyUserEnabled() {
        return flinkProxyUserEnabled;
    }

    public void setFlinkProxyUserEnabled(Boolean flinkProxyUserEnabled) {
        this.flinkProxyUserEnabled = flinkProxyUserEnabled;
    }

    public String getFsDefaultFs() {
        return fsDefaultFs;
    }

    public void setFsDefaultFs(String fsDefaultFs) {
        this.fsDefaultFs = fsDefaultFs == null ? null : fsDefaultFs.trim();
    }

    public String getFsWebhdfs() {
        return fsWebhdfs;
    }

    public void setFsWebhdfs(String fsWebhdfs) {
        this.fsWebhdfs = fsWebhdfs == null ? null : fsWebhdfs.trim();
    }

    public String getFsUser() {
        return fsUser;
    }

    public void setFsUser(String fsUser) {
        this.fsUser = fsUser == null ? null : fsUser.trim();
    }

    public String getFsDir() {
        return fsDir;
    }

    public void setFsDir(String fsDir) {
        this.fsDir = fsDir == null ? null : fsDir.trim();
    }

    public String getStreamingBlackNodeList() {
        return streamingBlackNodeList;
    }

    public void setStreamingBlackNodeList(String streamingBlackNodeList) {
        this.streamingBlackNodeList = streamingBlackNodeList == null ? null : streamingBlackNodeList.trim();
    }

    public String getBatchBlackNodeList() {
        return batchBlackNodeList;
    }

    public void setBatchBlackNodeList(String batchBlackNodeList) {
        this.batchBlackNodeList = batchBlackNodeList == null ? null : batchBlackNodeList.trim();
    }
}