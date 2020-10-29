package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.AuthUserRole;

import java.util.List;

public interface UserRoleService {


    List<AuthUserRole> getByUsername(String username);

}
