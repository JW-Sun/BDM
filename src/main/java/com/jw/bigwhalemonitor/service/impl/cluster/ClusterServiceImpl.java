package com.jw.bigwhalemonitor.service.impl.cluster;

import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.mapper.ClusterMapper;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterMapper clusterMapper;


    @Override
    public List<Cluster> getAll() {
        List<Cluster> clusters = clusterMapper.selectByExample(null);
        return clusters;
    }
}
