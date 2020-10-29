package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.AuthResource;
import com.jw.bigwhalemonitor.entity.AuthResourceExample;
import com.jw.bigwhalemonitor.mapper.AuthResourceMapper;
import com.jw.bigwhalemonitor.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private AuthResourceMapper authResourceMapper;

    /***
     *  获得所有的资源文件
     * @return
     */
    @Override
    public List<AuthResource> getAll() {
        List<AuthResource> authResources = authResourceMapper.selectByExample(null);
        return authResources;
    }

    @Override
    public AuthResource getByCode(String code) {
        AuthResourceExample example = new AuthResourceExample();
        AuthResourceExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        List<AuthResource> authResources = authResourceMapper.selectByExample(example);
        return authResources.size() == 0 ? null : authResources.get(0);
    }

    @Override
    public AuthResource save(AuthResource resource) {
        if (resource.getId() == null) {
            resource.setId(UUID.randomUUID().toString());
            int insert = authResourceMapper.insert(resource);
        } else {
            authResourceMapper.updateByPrimaryKey(resource);
        }
        return resource;
    }

    @Override
    public AuthResource getById(String id) {
        AuthResource authResource = authResourceMapper.selectByPrimaryKey(id);
        return authResource;
    }

    @Override
    public int delete(String id) {
        int i = authResourceMapper.deleteByPrimaryKey(id);
        return i;
    }
}
