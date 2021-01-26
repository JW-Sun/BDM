
package com.jw.bigwhalemonitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoYarnApp;
import com.jw.bigwhalemonitor.entity.YarnApp;
import com.jw.bigwhalemonitor.entity.YarnAppExample;
import com.jw.bigwhalemonitor.mapper.YarnAppMapper;
import com.jw.bigwhalemonitor.service.YarnAppService;
import com.jw.bigwhalemonitor.util.YarnApiUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class YarnAppServiceImpl implements YarnAppService {

    @Autowired
    private YarnAppMapper yarnAppMapper;

    @Override
    public PageInfo<YarnApp> getAllByQuery(DtoYarnApp req) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(req.getName())) {
            criteria.andNameLike(req.getName());
        }
        // uid+clusterId+appId
        if (StringUtils.isNotBlank(req.getUid())) {
            criteria.andUidEqualTo(req.getUid());
        }
        if (StringUtils.isNotBlank(req.getClusterId())) {
            criteria.andClusterIdEqualTo(req.getClusterId());
        }
        if (StringUtils.isNotBlank(req.getAppId())) {
            criteria.andAppIdEqualTo(req.getAppId());
        }
        PageHelper.startPage(req.pageNo - 1, req.pageSize);
        List<YarnApp> yarnApps = yarnAppMapper.selectByExample(example);
        PageInfo<YarnApp> pageInfo = new PageInfo<>(yarnApps);
        return pageInfo;
    }

    @Override
    public YarnApp getByAppId(String appId) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        criteria.andAppIdEqualTo(appId);
        List<YarnApp> yarnApps = yarnAppMapper.selectByExample(example);
        return yarnApps.size() > 0 ? yarnApps.get(0) : null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByAppId(String appId) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        criteria.andAppIdEqualTo(appId);
        yarnAppMapper.deleteByExample(example);
    }

    @Override
    public void deleteByClusterId(String clusterId) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(clusterId);
        yarnAppMapper.deleteByExample(example);
    }

    @Override
    public List<YarnApp> getByClusterId(String s) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(s);
        return yarnAppMapper.selectByExample(example);
    }

    @Override
    public void saveAll(List<YarnApp> appInfos) {
        for (YarnApp appInfo : appInfos) {
            if (appInfo.getId() == null) {
                appInfo.setId(UUID.randomUUID().toString());
                yarnAppMapper.insert(appInfo);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(List<String> ids) {
        for (String id : ids) {
            yarnAppMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public YarnApp getByScriptIdAndAppId(String scriptId, String jobId) {
        YarnAppExample example = new YarnAppExample();
        example.createCriteria().andScriptIdEqualTo(scriptId).andAppIdEqualTo(jobId);
        List<YarnApp> yarnApps = yarnAppMapper.selectByExample(example);
        if (yarnApps == null || yarnApps.size() == 0) {
            return null;
        }
        return yarnApps.get(0);
    }

    @Override
    public YarnApp getByScriptIdAndName(String scriptId, String name) {
        YarnAppExample example = new YarnAppExample();
        example.createCriteria().andScriptIdEqualTo(scriptId).andNameEqualTo(name);
        List<YarnApp> yarnApps = yarnAppMapper.selectByExample(example);
        if (yarnApps == null || yarnApps.size() == 0) {
            return null;
        }
        return yarnApps.get(0);
    }

    @Override
    public List<YarnApp> getByScriptId(String scriptIds) {
        YarnAppExample example = new YarnAppExample();
        YarnAppExample.Criteria criteria = example.createCriteria();
        criteria.andScriptIdEqualTo(scriptIds);
        return yarnAppMapper.selectByExample(example);
    }
}
