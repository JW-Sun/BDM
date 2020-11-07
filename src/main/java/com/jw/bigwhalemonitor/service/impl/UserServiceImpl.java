package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.AuthUser;
import com.jw.bigwhalemonitor.entity.AuthUserExample;
import com.jw.bigwhalemonitor.entity.AuthUserRole;
import com.jw.bigwhalemonitor.entity.AuthUserRoleExample;
import com.jw.bigwhalemonitor.mapper.AuthUserMapper;
import com.jw.bigwhalemonitor.mapper.AuthUserRoleMapper;
import com.jw.bigwhalemonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserRoleMapper userRoleMapper;

    @Override
    public List<AuthUser> getAllUser() {
        List<AuthUser> authUsers = authUserMapper.selectByExample(null);
        return authUsers;
    }

    @Override
    public List<AuthUser> getUserByUsername(String username) {
        AuthUserExample example = new AuthUserExample();
        AuthUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<AuthUser> authUsers = authUserMapper.selectByExample(example);
        return authUsers;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(AuthUser user) {
        if (user.getId() == null) {
            user.setCreateTime(new Date());
            user.setId(UUID.randomUUID().toString());
        }
        if (getUserByUsername(user.getUsername()).size() > 0) {
            delete(user);
        }
        user.setUpdateTime(new Date());
        user.setPassword(new StandardPasswordEncoder().encode(user.getPassword()));
        authUserMapper.insert(user);
        List<String> roles = user.getRoles();
        if (!roles.isEmpty()) {
            for (String role : roles) {
                AuthUserRole userRole = new AuthUserRole();
                userRole.setId(UUID.randomUUID().toString());
                userRole.setUsername(user.getUsername());
                userRole.setRole(role);
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(AuthUser user) {
        List<String> roles = user.getRoles();
        authUserMapper.deleteByPrimaryKey(user.getId());

        AuthUserRoleExample example = new AuthUserRoleExample();
        AuthUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        userRoleMapper.deleteByExample(example);

    }

    @Override
    public AuthUser getById(String uid) {
        return authUserMapper.selectByPrimaryKey(uid);
    }


}
