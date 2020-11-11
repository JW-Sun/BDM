package com.jw.bigwhalemonitor.service.cluster;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.entity.Cluster;

import java.util.List;

public interface ClusterService {

    public List<Cluster> getAll();

    List<Cluster> getByName(String name);

    Cluster save(Cluster cluster);

    PageInfo<Cluster> getAll(int pageNo, int pageSize);

    Cluster getById(String id);

    void delete(String id);

    Cluster getDefaultCluster();
}
