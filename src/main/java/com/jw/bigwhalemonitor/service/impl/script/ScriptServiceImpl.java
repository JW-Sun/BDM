package com.jw.bigwhalemonitor.service.impl.script;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.entity.AuthUser;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.entity.ScriptExample;
import com.jw.bigwhalemonitor.mapper.ScriptMapper;
import com.jw.bigwhalemonitor.service.UserService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.util.WebHdfsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private UserService userService;

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

    @Override
    public List<Script> getByUidAndClusterId(String uid, String clusterId) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andClusterIdEqualTo(clusterId);
        criteria.andUidEqualTo(uid);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

    @Override
    public List<Script> getByAgentId(String id) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(id);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

    @Override
    public List<Script> getByUid(String id) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(id);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

    @Override
    public List<Script> getAll() {
        return scriptMapper.selectByExample(null);
    }

    @Override
    public List<Script> getByName(String name) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

    @Override
    public Script getById(String id) {
        return scriptMapper.selectByPrimaryKey(id);
    }

    /***
     *  返回脚本信息中的任务执行的Jar包路径。
     * @param script
     * @return
     */
    @Override
    public String getJarsPath(String script) {
        String[] splitArr = script.split(" ");
        int jarIdx = -1;
        for (int i = 1; i < splitArr.length; i++) {
            String cs = splitArr[i];
            // --jars spark    -j/--jar flink on yarn-session
            if (cs.contains(".jar") || cs.contains(".py")) {
                // 前面不能是代表第三方
                String pre = splitArr[i - 1];
                if (!"--jars".equals(pre) && !"-j".equals(pre) && !"-jar".equals(pre)) {
                    // 找到任务执行的jar
                    jarIdx = i;
                    break;
                }
            }
        }
        if (jarIdx == -1) {
            return "";
        }
        return splitArr[jarIdx];
    }

    @Override
    public void deleteJar(Script scriptById) {
        // 获得脚本中的执行jar的路径
        String jarsPath = getJarsPath(scriptById.getScript());
        if (jarsPath != null && jarsPath.length() > 0) {
            // 需要检查这个jarspath是否还被引用
            boolean using = false;
            // 获得相同user的其他脚本任务
            List<Script> scriptByUidWithNotId = getByUidWithNotId(scriptById.getId(), scriptById.getUid());
            for (Script script : scriptByUidWithNotId) {
                if (jarsPath.equals(getJarsPath(script.getScript()))) {
                    // 还在使用中
                    using = true;
                    break;
                }
            }

            // 如果这个url还没有被使用过就可以尝试进行删除
            if (!using) {
                List<Cluster> allCluster = clusterService.getAll();
                for (Cluster cluster : allCluster) {
                    if (jarsPath.equals(cluster.getFsDefaultFs())) {
                        String fsDefaultFs = cluster.getFsDefaultFs();
                        // 去掉最后的 "/"
                        if (fsDefaultFs.endsWith("/")) {
                            fsDefaultFs = fsDefaultFs.substring(0, fsDefaultFs.length() - 1);
                        }
                        // 获得jar的绝对路径
                        jarsPath = jarsPath.substring(fsDefaultFs.length());
                        AuthUser user = userService.getById(scriptById.getUid());
                        // 删除
                        WebHdfsUtils.delete(cluster.getFsWebhdfs(), jarsPath, user.getUsername());
                        break;
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Script script) {
        if (script.getId() == null) {
            script.setId(UUID.randomUUID().toString());
            scriptMapper.insert(script);
        } else {
            scriptMapper.updateByPrimaryKey(script);
        }
    }

    @Override
    public Map<String, Script> getAppMap(String clusterId) {
        Map<String, Script> map = new HashMap<>();
        List<Script> byClusterId = getByClusterId(clusterId);
        if (byClusterId != null && byClusterId.size() > 0) {
            for (Script script : byClusterId) {
                if (script.getType() == Constant.SCRIPT_TYPE_SHELL_BATCH) {
                    continue;
                }
                String user = script.getUser();
                String queue = script.getQueue();
                if (queue != null && !"root".equals(queue) && !queue.startsWith("root.")) {
                    queue = "root." + queue;
                }
                map.put(user + "$" + queue + "$" + script.getApp(), script);
            }
        }
        return map;
    }

    private List<Script> getByUidWithNotId(String id, String uid) {
        ScriptExample example = new ScriptExample();
        ScriptExample.Criteria criteria = example.createCriteria();
        criteria.andIdNotEqualTo(id);
        criteria.andUidEqualTo(uid);
        List<Script> scripts = scriptMapper.selectByExample(example);
        return scripts;
    }

}
