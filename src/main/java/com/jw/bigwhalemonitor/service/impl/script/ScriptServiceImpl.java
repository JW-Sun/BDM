package com.jw.bigwhalemonitor.service.impl.script;

import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Override
    public List<Script> getFuzzyScriptList(DtoScript dtoScript) {

        return null;
    }

}
