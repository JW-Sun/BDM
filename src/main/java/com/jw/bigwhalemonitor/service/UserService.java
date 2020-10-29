package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.AuthUser;

import java.util.List;

public interface UserService {

    public List<AuthUser> getAllUser();

    List<AuthUser> getUserByUsername(String username);

    void save(AuthUser user);

    void delete(AuthUser user);

}
