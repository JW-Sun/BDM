package com.jw.bigwhalemonitor.controller.script;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.dto.DtoScript;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/script")
public class ScriptController extends BaseController {

    @Autowired
    private ScriptService scriptService;

    @PostMapping("/getpage.api")
    public Msg getPage(@RequestBody DtoScript dtoScript) {
        LoginUser currentUser = getCurrentUser();
        if (!currentUser.isRoot()) {
            dtoScript.setUid(currentUser.getId());
        }

        return null;
    }


    // 脚本任务的参数分配
    private Map<String, Integer> calResource(int type, String script) {
        Map<String, Integer> result = new HashMap<>();

        // 对spark任务的参数进行设置
        Map<String, String> spark = new HashMap<>();
        spark.put("--driver-memory", "512M");
        spark.put("--driver-cores", "1");
        spark.put("--executor-memory", "1024M");
        spark.put("--num-executors", "1");
        spark.put("--executor-cores", "1");
        spark.put("spark.yarn.executor.memoryOverhead", "-");

        // 对flink任务进行参数设置
        Map<String, String> flink = new HashMap<>();
        flink.put("-yjm", "512M");
        flink.put("-ytm", "1024M");
        flink.put("-yn", "1");
        flink.put("-ys", "1");

        String[] tokens = script.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (type == Constant.SCRIPT_TYPE_SPARK_STREAMING || type == Constant.SCRIPT_TYPE_SPARK_BATCH) {
                // 在executor执行的时候，使用的内存可能会超过executor-memory，因此需要为其额外预留一部分资源
                if (token.startsWith("spark.yarn.executor.memoryOverhead")) {
                    spark.put("spark.yarn.executor.memoryOverhead", token.split("=")[1]);
                } else if (spark.containsKey(token)) {
                    spark.put(token, tokens[i + 1]);
                }
            } else {
                if (flink.containsKey(token)) {
                    flink.put(token, tokens[i + 1]);
                }
            }
        }

        // 如果是spark 流计算类型或者是spark批处理类型
        if (type == Constant.SCRIPT_TYPE_SPARK_STREAMING || type == Constant.SCRIPT_TYPE_SPARK_BATCH) {
            String driverMemoryStr = spark.get("--driver-memory").toUpperCase();
            // driver 的内存
            int driverMemory = parseMemory(driverMemoryStr);
            String executorMemoryStr = spark.get("--executor-memory");
            // executor 的内存
            int executorMemory = parseMemory(executorMemoryStr);
            int numExecutors = Integer.parseInt(spark.get("--num-executors"));
            String memoryOverheadStr = spark.get("spark.yarn.executor.memoryOverhead");
            if (!"-".equals(memoryOverheadStr)) {
                int memoryOverhead = parseMemory(memoryOverheadStr);
                result.put("totalMemory", numExecutors * (executorMemory + memoryOverhead) + (driverMemory + memoryOverhead));
            } else {
                result.put("totalMemory", (int)(numExecutors * (executorMemory + Math.max(executorMemory * 0.1, 384)) + (driverMemory + Math.max(driverMemory * 0.1, 384))));
            }
            // 核心数量
            int driverCores = Integer.parseInt(spark.get("--driver-cores"));
            int executorCores = Integer.parseInt(spark.get("--executor-cores"));
            result.put("totalCores", numExecutors * executorCores + driverCores);
        } else {
            // jobmanager内存大小
            String yjmStr = flink.get("-yjm").toUpperCase();
            int yjm = parseMemory(yjmStr);

            // taskManager内存大小
            String ytmStr = flink.get("-ytm").toUpperCase();
            int ytm = parseMemory(ytmStr);

            // taskManager 的刷领
            int yn = Integer.parseInt(flink.get("-yn"));
            result.put("totalMemory", yn * ytm + yjm);

            // slot的数量
            int ys = Integer.parseInt(flink.get("-ys"));
            result.put("totalCores", yn * ys + 1);
        }
        return result;
    }

    /***
     *  解析内存的大小
     * @param s
     * @return
     */
    private int parseMemory(String s) {
        s = s.toUpperCase();
        int val;
        if (s.contains("M")) {
            val = Integer.parseInt(s.substring(0, s.indexOf("M")));
        } else if (s.contains("G")) {
            val = Integer.parseInt(s.substring(0, s.indexOf("G")));
        } else {
            val = Integer.parseInt(s);
        }
        return val;
    }

}
