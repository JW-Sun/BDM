package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.AuthRole;
import com.jw.bigwhalemonitor.entity.AuthRoleExample;
import com.jw.bigwhalemonitor.entity.AuthRoleResource;
import com.jw.bigwhalemonitor.entity.AuthRoleResourceExample;
import com.jw.bigwhalemonitor.mapper.AuthRoleMapper;
import com.jw.bigwhalemonitor.mapper.AuthRoleResourceMapper;
import com.jw.bigwhalemonitor.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.el.PropertyNotWritableException;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AuthRoleMapper roleMapper;

    @Autowired
    private AuthRoleResourceMapper roleResourceMapper;

    @Override
    public List<AuthRole> getAll() {
        List<AuthRole> authRoles = roleMapper.selectByExample(null);
        return authRoles;
    }

    @Override
    public List<AuthRole> getByCode(String code) {
        AuthRoleExample example = new AuthRoleExample();
        AuthRoleExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        List<AuthRole> authRoles = roleMapper.selectByExample(example);
        return authRoles;
    }

    @Override
    @Transactional
    public AuthRole save(AuthRole role) {
        String code = role.getCode();
        AuthRoleResourceExample example = new AuthRoleResourceExample();
        AuthRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleEqualTo(code);
        int i = roleResourceMapper.deleteByExample(example);

        List<String> resources = role.getResources();
        if (resources.size() > 0) {
            for (String resource : resources) {
                AuthRoleResource authRoleResource = new AuthRoleResource();
                authRoleResource.setId(UUID.randomUUID().toString());
                authRoleResource.setRole(code);
                authRoleResource.setResource(resource);
                roleResourceMapper.insert(authRoleResource);
            }
        }

        if (role.getId() == null) {
            role.setId(UUID.randomUUID().toString());
            roleMapper.insert(role);
        } else {
            roleMapper.updateByPrimaryKey(role);
        }

        return role;
    }

    @Override
    public void deleteById(String id) {
        AuthRole authRole = roleMapper.selectByPrimaryKey(id);
        roleMapper.deleteByPrimaryKey(id);
        AuthRoleResourceExample example = new AuthRoleResourceExample();
        AuthRoleResourceExample.Criteria criteria = example.createCriteria();
        criteria.andRoleEqualTo(authRole.getCode());
        roleResourceMapper.deleteByExample(example);
    }
}
