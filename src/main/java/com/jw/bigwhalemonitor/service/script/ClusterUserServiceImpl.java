package com.jw.bigwhalemonitor.service.script;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoClusterUser;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.ClusterUserExample;
import com.jw.bigwhalemonitor.mapper.ClusterUserMapper;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClusterUserServiceImpl implements ClusterUserService {

    @Autowired
    private ClusterUserMapper clusterUserMapper;


    @Override
    public PageInfo<ClusterUser> getByParam(DtoClusterUser dtoClusterUser) {
        ClusterUserExample example = new ClusterUserExample();
        ClusterUserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(dtoClusterUser.getQueue())) {
            criteria.andQueueLike(dtoClusterUser.getQueue());
        }
        if (StringUtils.isNotBlank(dtoClusterUser.getUid())) {
            criteria.andUidLike(dtoClusterUser.getUid());
        }
        if (StringUtils.isNotBlank(dtoClusterUser.getClusterId())) {
            criteria.andClusterIdLike(dtoClusterUser.getClusterId());
        }
        PageHelper.startPage(dtoClusterUser.pageNo - 1, dtoClusterUser.pageSize);
        List<ClusterUser> clusterUsers = clusterUserMapper.selectByExample(example);
        PageInfo<ClusterUser> pageInfo = new PageInfo<>(clusterUsers);
        return pageInfo;
    }

    @Override
    public List<ClusterUser> getUidAndClusterId(String uid, String clusterId) {
        ClusterUserExample example = new ClusterUserExample();
        ClusterUserExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andClusterIdEqualTo(clusterId);
        List<ClusterUser> clusterUsers = clusterUserMapper.selectByExample(example);
        return clusterUsers;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAll(List<ClusterUser> clusterUsers) {
        for (ClusterUser clusterUser : clusterUsers) {
            if (clusterUser.getId() == null) {
                clusterUser.setId(UUID.randomUUID().toString());
                clusterUserMapper.insert(clusterUser);
            } else {
                clusterUserMapper.updateByPrimaryKey(clusterUser);
            }
        }
    }

    @Override
    public ClusterUser getById(String id) {
        ClusterUser clusterUser = clusterUserMapper.selectByPrimaryKey(id);
        return clusterUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deteleById(String id) {
        clusterUserMapper.deleteByPrimaryKey(id);
    }
}
