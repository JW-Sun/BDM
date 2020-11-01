package com.jw.bigwhalemonitor.controller.admin.cluster;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoCluster;
import com.jw.bigwhalemonitor.dto.DtoClusterUser;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
*   用来管理集群的用户
* */

@RestController
@RequestMapping("/admin/cluster/cluster_user")
public class AdminClusterUserController extends BaseController {

    @Autowired
    private ClusterUserService clusterUserService;

    @Autowired
    private ClusterService clusterService;

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoClusterUser dtoClusterUser) {
        // 在集群用户中定义了队列的定义，意图就是使用的yarn队列的相关理论
        PageInfo<ClusterUser> clusterUserList = clusterUserService.getByParam(dtoClusterUser);
        List<ClusterUser> list = clusterUserList.getList();
        List<DtoClusterUser> content = new ArrayList<>();
        for (ClusterUser clusterUser : list) {
            DtoClusterUser dtoClusterUser1 = new DtoClusterUser();
            BeanUtils.copyProperties(clusterUser, dtoClusterUser1);
            content.add(dtoClusterUser1);
        }
        Page<DtoClusterUser> page = new PageImpl<DtoClusterUser>(content);
        return success(page);
    }

    @PostMapping("/save.api")
    public Msg save(@RequestBody DtoClusterUser dtoClusterUser) {
        String validate = dtoClusterUser.validate();
        if (validate != null) {
            return failed(validate);
        }
        if (dtoClusterUser.getId() == null) {
            List<ClusterUser> clusterUsers = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            String[] clusterIdSplit = dtoClusterUser.getClusterId().split(",");
            for (String clusterId : clusterIdSplit) {
                List<ClusterUser> uidAndClusterId = clusterUserService.getUidAndClusterId(dtoClusterUser.getUid(), clusterId);
                if (!uidAndClusterId.isEmpty()) {
                    Cluster byId = clusterService.getById(clusterId);
                    errors.add(byId.getName());
                } else {
                    ClusterUser clusterUser = new ClusterUser();
                    BeanUtils.copyProperties(dtoClusterUser, clusterUser);
                    clusterUser.setClusterId(clusterId);
                    clusterUsers.add(clusterUser);
                }
            }
            if (errors.isEmpty()) {
                clusterUserService.saveAll(clusterUsers);
            } else {
                return failed("用户重复 " + JSON.toJSONString(errors));
            }
        }
        return success();
    }

}
