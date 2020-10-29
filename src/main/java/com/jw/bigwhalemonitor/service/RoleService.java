package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.AuthRole;

import java.util.List;

public interface RoleService {
    List<AuthRole> getAll();

    List<AuthRole> getByCode(String code);

    AuthRole save(AuthRole role);

    void deleteById(String id);

}
