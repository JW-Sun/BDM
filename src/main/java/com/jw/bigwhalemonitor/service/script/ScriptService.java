package com.jw.bigwhalemonitor.service.script;

import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.Script;

import java.util.List;
import java.util.Map;

public interface ScriptService {

    /***
     *  模糊条件查询
     * @param dtoScript
     * @return
     */
    public List<Script> getFuzzyScriptList(DtoScript dtoScript);

    List<Script> getByClusterId(String id);

    List<Script> getByUidAndClusterId(String uid, String clusterId);

    List<Script> getByAgentId(String id);

    List<Script> getByUid(String id);

    List<Script> getAll();

    List<Script> getByName(String name);

    Script getById(String id);

    String getJarsPath(String script);

    void deleteJar(Script scriptById);

    void save(Script script);

	Map<String, Script> getAppMap(String clusterId);
}
