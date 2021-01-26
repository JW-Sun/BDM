package com.jw.bigwhalemonitor.service;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoYarnApp;
import com.jw.bigwhalemonitor.entity.YarnApp;

import java.util.List;

public interface YarnAppService {
    PageInfo<YarnApp> getAllByQuery(DtoYarnApp req);

    YarnApp getByAppId(String appId);

    void deleteByAppId(String appId);

    void deleteByClusterId(String clusterId);

    List<YarnApp> getByClusterId(String s);

    void saveAll(List<YarnApp> appInfos);

    void deleteById(List<String> ids);

    YarnApp getByScriptIdAndAppId(String scriptId, String jobId);

    YarnApp getByScriptIdAndName(String scriptId, String name);

	List<YarnApp> getByScriptId(String scriptIds);
}
