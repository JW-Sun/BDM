package com.jw.bigwhalemonitor.controller.cluster;

import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoAgent;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cluster/agent")
public class AgentController extends BaseController {

    @Autowired
    private AgentService agentService;

    @GetMapping("/getall.api")
    public Msg getAllAgent() {
        List<DtoAgent> collect = agentService.getAll()
                .stream()
                .map((item) -> {
                    DtoAgent dtoAgent = new DtoAgent();
                    BeanUtils.copyProperties(item, dtoAgent);
                    return dtoAgent;
                })
                .collect(Collectors.toList());
        return success(collect);
    }

}
