package com.jw.bigwhalemonitor.service.cluster;

import com.jw.bigwhalemonitor.dto.DtoComputeFramework;
import com.jw.bigwhalemonitor.entity.ComputeFramework;

import java.util.List;

public interface ComputerFrameworkService {
    List<ComputeFramework> getByTypeAndVersion(String type, String version);

    void save(ComputeFramework computeFramework);

    void deleteById(String id);

    List<ComputeFramework> getAll(DtoComputeFramework dtoComputeFramework);
}
