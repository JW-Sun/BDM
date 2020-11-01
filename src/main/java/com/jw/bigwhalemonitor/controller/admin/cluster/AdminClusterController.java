package com.jw.bigwhalemonitor.controller.admin.cluster;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoCluster;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.util.WebHdfsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
*   集群管理，针对集群的信息比如地址，授权用户的管理
* */

@RestController
@RequestMapping("/admin/cluster")
public class AdminClusterController extends BaseController {

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private AgentService agentService;

    @PostMapping("/save.api")
    public Msg saveCluster(@RequestBody DtoCluster dtoCluster) {
        String validate = dtoCluster.validate();
        if (validate != null) {
            return failed(validate);
        }
        if (dtoCluster.getId() == null) {
            List<Cluster> byName = clusterService.getByName(dtoCluster.getName());
            if (!byName.isEmpty()) {
                return failed("name has existed");
            }
        }
        boolean mkdir = WebHdfsUtils.mkdir(dtoCluster.getFsWebhdfs(), dtoCluster.getFsDir(), dtoCluster.getFsUser(), dtoCluster.getFsUser());
        if (!mkdir) {
            return failed("创建目录失败。");
        }
        Cluster cluster = new Cluster();
        BeanUtils.copyProperties(dtoCluster, cluster);
        Cluster save = clusterService.save(cluster);
        if (dtoCluster.getId() == null) {
            dtoCluster.setId(save.getId());
        }
        return success(dtoCluster);
    }

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoCluster dtoCluster) {
        PageInfo<Cluster> all = clusterService.getAll(dtoCluster.pageNo - 1, dtoCluster.pageSize);
        List<Cluster> list = all.getList();
        List<DtoCluster> content = new ArrayList<>();
        for (Cluster cluster : list) {
            DtoCluster dtoCluster1 = new DtoCluster();
            BeanUtils.copyProperties(cluster, dtoCluster1);
            content.add(dtoCluster1);
        }
        // transform
        Page<DtoCluster> page = new PageImpl<DtoCluster>(content);

        return success(page);
    }

    @PostMapping("/delete.api")
    public Msg deleteCluster(@RequestParam String id) {
        Cluster cluster = clusterService.getById(id);
        if (cluster != null) {
            List<Script> byClusterId = scriptService.getByClusterId(id);
            if (!byClusterId.isEmpty()) {
                return failed("集群存在脚本，需要提前删除");
            }
            List<Agent> clusters = agentService.getByClusterId(id);
            if (!clusters.isEmpty()) {
                return failed("代理中存在，需要提前删除");
            }
            clusterService.delete(id);
        }
        return success("删除成功");
    }

}
