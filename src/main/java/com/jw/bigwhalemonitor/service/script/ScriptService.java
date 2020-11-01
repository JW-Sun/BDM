package com.jw.bigwhalemonitor.service.script;

import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.Script;

import java.util.List;

public interface ScriptService {

    /***
     *  模糊条件查询
     * @param dtoScript
     * @return
     */
    public List<Script> getFuzzyScriptList(DtoScript dtoScript);

    List<Script> getByClusterId(String id);

}
