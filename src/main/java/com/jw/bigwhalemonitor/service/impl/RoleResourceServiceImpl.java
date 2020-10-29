package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.AuthRoleResource;
import com.jw.bigwhalemonitor.entity.AuthRoleResourceExample;
import com.jw.bigwhalemonitor.mapper.AuthRoleResourceMapper;
import com.jw.bigwhalemonitor.service.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private AuthRoleResourceMapper roleResourceMapper;

    @Override
    public List<AuthRoleResource> getByRole(String code) {
        AuthRoleResourceExample example = new AuthRoleResourceExample();
        AuthRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleEqualTo(code);
        List<AuthRoleResource> authRoleResources = roleResourceMapper.selectByExample(example);
        return authRoleResources;
    }
}
