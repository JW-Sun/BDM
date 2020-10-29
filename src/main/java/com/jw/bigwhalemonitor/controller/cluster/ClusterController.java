package com.jw.bigwhalemonitor.controller.cluster;

import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoCluster;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cluster")
public class ClusterController extends BaseController {

    @Autowired
    private ClusterService clusterService;

    @GetMapping("/getall.api")
    public Msg getAll() {
        List<Cluster> all = clusterService.getAll();
        List<DtoCluster> res = new ArrayList<>();
        if (all == null || all.size() == 0) {
            return success("getall cluster success", res);
        }
        for (Cluster cluster : all) {
            DtoCluster dtoCluster = new DtoCluster();
            BeanUtils.copyProperties(cluster, dtoCluster);
            res.add(dtoCluster);
        }
        return success("getall cluster success", res);
    }
}
