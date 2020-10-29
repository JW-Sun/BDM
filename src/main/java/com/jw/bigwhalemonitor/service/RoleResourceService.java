package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.AuthRoleResource;

import java.util.List;

public interface RoleResourceService {

    List<AuthRoleResource> getByRole(String code);

}
