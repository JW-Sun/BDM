package com.jw.bigwhalemonitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.bigwhalemonitor.dto.DtoScheduling;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.SchedulingExample;
import com.jw.bigwhalemonitor.mapper.SchedulingMapper;
import com.jw.bigwhalemonitor.service.SchedulingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private SchedulingMapper schedulingMapper;

    /***
     *  通过请求实体类获得对应的分页信息
     *  id uid type scriptId
     * @param req
     * @return
     */
    @Override
    public PageInfo<DtoScheduling> getByPageQuery(DtoScheduling req) {
        SchedulingExample example = new SchedulingExample();
        SchedulingExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(req.getId())) {
            criteria.andIdEqualTo(req.getId());
        }
        if (StringUtils.isNotBlank(req.getUid())) {
            criteria.andUidEqualTo(req.getUid());
        }
        if (req.getType() != null) {
            criteria.andTypeEqualTo(req.getType());
        }
        if (StringUtils.isNotBlank(req.getScriptId())) {
            criteria.andScriptIdsEqualTo(req.getScriptId());
        }
        PageHelper.startPage(req.pageNo - 1, req.pageSize);
        List<Scheduling> schedulings = schedulingMapper.selectByExample(example);
        List<DtoScheduling> dtoSchedulings = new ArrayList<>();
        for (Scheduling scheduling : schedulings) {
            DtoScheduling dtoScheduling = new DtoScheduling();
            dtoScheduling.pageNo = req.pageNo;
            dtoScheduling.pageSize = req.pageSize;
            BeanUtils.copyProperties(scheduling, dtoScheduling);
            //scriptId
            dtoScheduling.setScriptIds(Arrays.asList(scheduling.getScriptIds().split(",")));
            // week
            if (StringUtils.isNotBlank(scheduling.getWeek())) {
                dtoScheduling.setWeek(Arrays.asList(scheduling.getWeek().split(",")));
            }
            // dingding
            if (StringUtils.isNotBlank(scheduling.getDingdingHooks())) {
                dtoScheduling.setDingdingHooks(Arrays.asList(scheduling.getDingdingHooks().split(",")));
            }
            dtoSchedulings.add(dtoScheduling);
        }
        return new PageInfo<>(dtoSchedulings);
    }

    @Override
    public Scheduling getById(String id) {
        return schedulingMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Scheduling save(Scheduling scheduling) {
        schedulingMapper.insert(scheduling);
        return scheduling;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        schedulingMapper.deleteByPrimaryKey(id);
    }
}
