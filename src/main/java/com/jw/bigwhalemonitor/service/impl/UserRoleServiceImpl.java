package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.AuthUserRole;
import com.jw.bigwhalemonitor.entity.AuthUserRoleExample;
import com.jw.bigwhalemonitor.mapper.AuthUserRoleMapper;
import com.jw.bigwhalemonitor.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private AuthUserRoleMapper userRoleMapper;

    @Override
    public List<AuthUserRole> getByUsername(String username) {
        AuthUserRoleExample example = new AuthUserRoleExample();
        AuthUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<AuthUserRole> authUserRoles = userRoleMapper.selectByExample(example);
        return authUserRoles;
    }
}
