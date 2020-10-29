package com.jw.bigwhalemonitor.service;

import com.jw.bigwhalemonitor.entity.AuthResource;

import java.util.List;

public interface ResourceService {

    public List<AuthResource> getAll();

    public AuthResource getByCode(String code);

    public AuthResource save(AuthResource resource);

    public AuthResource getById(String id);

    public int delete(String id);


}
