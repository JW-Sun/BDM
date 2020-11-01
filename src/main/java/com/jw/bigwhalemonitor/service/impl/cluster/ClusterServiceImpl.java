package com.jw.bigwhalemonitor.service.impl.cluster;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterExample;
import com.jw.bigwhalemonitor.entity.ClusterUserExample;
import com.jw.bigwhalemonitor.mapper.ClusterMapper;
import com.jw.bigwhalemonitor.mapper.ClusterUserMapper;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private ClusterUserMapper clusterUserMapper;

    @Override
    public List<Cluster> getAll() {
        List<Cluster> clusters = clusterMapper.selectByExample(null);
        return clusters;
    }

    @Override
    public List<Cluster> getByName(String name) {
        ClusterExample example = new ClusterExample();
        ClusterExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Cluster> clusters = clusterMapper.selectByExample(example);
        return clusters;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Cluster save(Cluster cluster) {
        if (cluster.getDefaultFileCluster()) {
            // 将其他的置为非默认
            ClusterExample example = new ClusterExample();
            ClusterExample.Criteria criteria = example.createCriteria();
            criteria.andDefaultFileClusterEqualTo(true);
            List<Cluster> clusters = clusterMapper.selectByExample(example);
            for (Cluster cluster1 : clusters) {
                cluster1.setDefaultFileCluster(false);
                clusterMapper.updateByPrimaryKey(cluster1);
            }
        }
        if (cluster.getId() == null) {
            cluster.setId(UUID.randomUUID().toString());
            clusterMapper.insert(cluster);
        } else {
            clusterMapper.updateByPrimaryKey(cluster);
        }
        return cluster;
    }

    @Override
    public PageInfo<Cluster> getAll(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Cluster> clusters = clusterMapper.selectByExample(null);
        PageInfo<Cluster> clusterPageInfo = new PageInfo<>(clusters);
        return clusterPageInfo;
    }

    @Override
    public Cluster getById(String id) {
        Cluster cluster = clusterMapper.selectByPrimaryKey(id);
        return cluster;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        ClusterUserExample example = new ClusterUserExample();
        ClusterUserExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(id);
        clusterUserMapper.deleteByExample(example);

        clusterMapper.deleteByPrimaryKey(id);
    }


}
