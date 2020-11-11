package com.jw.bigwhalemonitor.service;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoYarnApp;
import com.jw.bigwhalemonitor.entity.YarnApp;

public interface YarnAppService {
    PageInfo<YarnApp> getAllByQuery(DtoYarnApp req);

    YarnApp getByAppId(String appId);

    void deleteByAppId(String appId);
}
