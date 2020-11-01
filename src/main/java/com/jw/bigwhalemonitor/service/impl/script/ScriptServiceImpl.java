package com.jw.bigwhalemonitor.service.impl.script;

import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.ScriptExample;
import com.jw.bigwhalemonitor.mapper.ScriptMapper;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Override
    public List<Script> getFuzzyScriptList(DtoScript dtoScript) {

        return null;
    }

    @Override
    public List<Script> getByClusterId(String id) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(id);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

}
