package com.jw.bigwhalemonitor.service.impl;

import com.jw.bigwhalemonitor.dto.DtoComputeFramework;
import com.jw.bigwhalemonitor.entity.ComputeFramework;
import com.jw.bigwhalemonitor.entity.ComputeFrameworkExample;
import com.jw.bigwhalemonitor.mapper.ComputeFrameworkMapper;
import com.jw.bigwhalemonitor.service.cluster.ComputerFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComputerFrameworkServiceImpl implements ComputerFrameworkService {

    @Autowired
    private ComputeFrameworkMapper computeFrameworkMapper;

    @Override
    public List<ComputeFramework> getByTypeAndVersion(String type, String version) {
        ComputeFrameworkExample example = new ComputeFrameworkExample();
        ComputeFrameworkExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andVersionEqualTo(version);
        List<ComputeFramework> computeFrameworks = computeFrameworkMapper.selectByExample(example);
        return computeFrameworks;
    }

    @Override
    public void save(ComputeFramework computeFramework) {
        if (computeFramework.getId() == null) {
            computeFramework.setId(UUID.randomUUID().toString());
            computeFrameworkMapper.insert(computeFramework);
        } else {
            computeFrameworkMapper.updateByPrimaryKey(computeFramework);
        }
    }

    @Override
    public void deleteById(String id) {
        computeFrameworkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ComputeFramework> getAll(DtoComputeFramework dtoComputeFramework) {
        ComputeFrameworkExample example = new ComputeFrameworkExample();
        ComputeFrameworkExample.Criteria criteria = example.createCriteria();
        if (dtoComputeFramework.getVersion() != null) {
            criteria.andVersionEqualTo(dtoComputeFramework.getVersion());
        }
        if (dtoComputeFramework.getType() != null) {
            criteria.andTypeEqualTo(dtoComputeFramework.getType());
        }
        List<ComputeFramework> computeFrameworks = computeFrameworkMapper.selectByExample(example);
        return computeFrameworks;
    }
}
