package com.jw.bigwhalemonitor.service.cluster;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoClusterUser;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClusterUserService {
    PageInfo<ClusterUser> getByParam(DtoClusterUser dtoClusterUser);

    List<ClusterUser> getUidAndClusterId(String uid, String clusterId);

    void saveAll(List<ClusterUser> clusterUsers);
}