package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.SchedulingExample;
import com.jw.bigwhalemonitor.mapper.SchedulingMapper;
import com.jw.bigwhalemonitor.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulingMapper schedulingMapper;

    @Override
    public List<Scheduling> getByScriptId(String id) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        criteria.andScriptIdsEqualTo(id);
        List<Scheduling> schedulings = schedulingMapper.selectByExample(example);
        return schedulings;
    }
}
