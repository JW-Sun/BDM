package com.jw.bigwhalemonitor.service;

import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoScheduling;
import com.jw.bigwhalemonitor.entity.Scheduling;

public interface SchedulingService {
    PageInfo<DtoScheduling> getByPageQuery(DtoScheduling req);

    Scheduling getById(String id);

    Scheduling save(Scheduling scheduling);

    void deleteById(String id);
}
