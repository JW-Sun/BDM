package com.jw.bigwhalemonitor.controller.admin.cluster;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoCluster;
import com.jw.bigwhalemonitor.dto.DtoClusterUser;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private ScriptService scriptService;

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
        } else {
            // 对于进行修改操作的用户来说需要检验是否有相同的集群用户
            ClusterUser clusterUser = clusterUserService.getById(dtoClusterUser.getId());
            if (clusterUser == null) {
                return failed("不存在需要修改集群用户");
            }
            // 根据uid和clusterId进行查找，避免重复
            List<ClusterUser> andClusterId = clusterUserService.getUidAndClusterId(dtoClusterUser.getUid(), dtoClusterUser.getClusterId());
            if (andClusterId.size() > 0 && !andClusterId.get(0).getId().equals(dtoClusterUser.getId())) {
                return failed("重复用户存在相同uid clusterid");
            }
            ClusterUser clusterUser1 = new ClusterUser();
            BeanUtils.copyProperties(dtoClusterUser, clusterUser1);
            List<ClusterUser> list = new ArrayList<>();
            list.add(clusterUser1);
            clusterUserService.saveAll(list);
        }
        return success();
    }

    @PostMapping("/delete.api")
    public Msg deleteById(@RequestParam String id) {
        // 先检查是否有该id对应
        ClusterUser byId = clusterUserService.getById(id);
        if (byId == null) {
            return failed("no exist");
        }
        List<Script> uc = scriptService.getByUidAndClusterId(byId.getUid(), byId.getClusterId());
        if (uc.size() > 0) {
            return failed("脚本中需要删除");
        }
        clusterUserService.deteleById(id);
        return success();
    }



}
