package com.jw.bigwhalemonitor.controller.yarn;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.dto.DtoYarnApp;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.YarnAppService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/yarn_app")
public class YarnAppController extends BaseController {

    @Autowired
    private YarnAppService yarnAppService;
    @Autowired
    private ClusterService clusterService;

    /***
     *  根据请求返回Yarn应用的列表
     * @param req
     * @return
     */
    @RequestMapping(value = "/getpage.api", method = RequestMethod.POST)
    public Msg getPage(@RequestBody DtoYarnApp req) {
        LoginUser user = getCurrentUser();
        if (!user.isRoot()) {
            req.setUid(user.getId());
        }
        Map<String, String> map = new HashMap<>();
        // 将name信息、appId信息、uid信息、clusterId信息的限制条件加入到带限制条件的哈希表中
        List<String> tokens = new ArrayList<>();
        if (StringUtils.isNotBlank(req.getName())) {
            tokens.add("name?" + req.getName());
        }
        if (StringUtils.isNotBlank(req.getAppId())) {
            tokens.add("appId=" + req.getAppId());
        }
        if (StringUtils.isNotBlank(req.getUid())) {
            tokens.add("uid=" + req.getUid());
        }
        if (StringUtils.isNotBlank(req.getClusterId())) {
            tokens.add("clusterId=" + req.getClusterId());
        }
        PageInfo<YarnApp> pageInfoByQUery = yarnAppService.getAllByQuery(req);
        List<YarnApp> list = pageInfoByQUery.getList();
        List<DtoYarnApp> content = new ArrayList<>();
        for (YarnApp yarnApp : list) {
            DtoYarnApp dtoYarnApp = new DtoYarnApp();
            BeanUtils.copyProperties(yarnApp, dtoYarnApp);
            content.add(dtoYarnApp);
        }
        // 将查询到的分页的数据放到数据ℹ中
        PageImpl<DtoYarnApp> page = new PageImpl(content);
        return success(page);
    }

    @RequestMapping(value = "/kill.api", method = RequestMethod.POST)
    public Msg kill(@RequestParam String appId) {
        // 根据appId进行查找,appid是唯一的
        YarnApp yarnAppById = yarnAppService.getByAppId(appId);
        if (yarnAppById == null) {
            return failed("要kill的Yarn应用是不存在，无法kill");
        }
        // 通过查到的yarn应用获得的对应的集群Id
        Cluster clusterById = clusterService.getById(yarnAppById.getClusterId());
        boolean isKillSuccess = YarnApiUtils.killApp(clusterById.getYarnUrl(), yarnAppById.getAppId());
        // 如果通过命令使得Yarn应用成功停掉，那么删去数据库中对应的Yarn应用信息
        if (isKillSuccess) {
            yarnAppService.deleteByAppId(appId);
            return success("yarn app is killed successfully");
        }
        return failed("yarn app 取消失败。");
    }

}
