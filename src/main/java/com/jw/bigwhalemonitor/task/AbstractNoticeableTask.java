package com.jw.bigwhalemonitor.task;

import com.jw.bigwhalemonitor.common.Constant;
import com.jw.bigwhalemonitor.common.pojo.HttpYarnApp;
import com.jw.bigwhalemonitor.entity.Agent;
import com.jw.bigwhalemonitor.entity.AuthUser;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.entity.ClusterUser;
import com.jw.bigwhalemonitor.entity.CmdRecord;
import com.jw.bigwhalemonitor.entity.Scheduling;
import com.jw.bigwhalemonitor.entity.Script;
import com.jw.bigwhalemonitor.service.UserService;
import com.jw.bigwhalemonitor.service.cluster.AgentService;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.service.cluster.ClusterUserService;
import com.jw.bigwhalemonitor.service.script.ScriptService;
import com.jw.bigwhalemonitor.util.MsgTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/***
 *  作用于为任务进行报警的抽象类
 */
public abstract class AbstractNoticeableTask {

    @Autowired
    private UserService userService;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private AgentService agentService;
    // @Autowired
    // private NoticeService noticeService;
    @Autowired
    private ClusterUserService clusterUserService;

    /***
     *  设置对于任务的提醒功能
     * @param cmdRecord
     * @param scheduling
     * @param appId
     * @param errorType
     */
    protected void notice(CmdRecord cmdRecord, Scheduling scheduling, String appId, String errorType) {
        Script script = null;
        AuthUser user = null;
        String email = null;
        String dingDingHooks = null;
        // 处理scheduling或者cmdRecord的部分
        if (scheduling != null) {
            if (scheduling.getType() == Constant.SCHEDULING_TYPE_STREAMING) {
                script = scriptService.getById(scheduling.getScriptIds());
            } else {
                script = scriptService.getById(cmdRecord.getScriptId());
            }
            user = userService.getById(scheduling.getUid());
            email = scheduling.getSendEmail() ? user.getEmail() : null;
            dingDingHooks = scheduling.getDingdingHooks();
        } else if (cmdRecord != null) {
            // 手动执行
            script = scriptService.getById(cmdRecord.getScriptId());
            user = userService.getById(cmdRecord.getUid());
            email = user.getEmail();
        }
        if (StringUtils.isBlank(email) && StringUtils.isBlank(dingDingHooks)) {
            return;
        }
        String userName = user.getNickname() == null ? user.getUsername() : user.getNickname();
        String content;
        // 脚本类型不是SHELL_BATCH的时候
        if (script.getType() != Constant.SCRIPT_TYPE_SHELL_BATCH) {
            // 找到对应的cluster
            Cluster cluster = clusterService.getById(script.getClusterId());
            // 集群对应的yarn
            String trackingUrl = cluster.getYarnUrl();
            if (StringUtils.isNotBlank(appId)) {
                trackingUrl = trackingUrl + "/cluster/app/" + appId;
            }
            // 设置好报警的信息
            content = MsgTools.getPlainErrorMsg(cluster.getName(), trackingUrl, userName, script.getName(), errorType);
        } else {
            // 获得脚本中设置的代理对象
            Agent agent = agentService.getById(script.getAgentId());
            content = MsgTools.getPlainErrorMsg(agent.getName(), userName, script.getName(), errorType);
        }
        // notice报警邮件的发送 TODO
        if (StringUtils.isNotBlank(email)) {
            // noticeService.sendEmail(email, content);
        }
        //发送钉钉
        // boolean publicWatchFlag = scheduling != null;
        // if (StringUtils.isNotBlank(dingDingHooks)) {
        //     String[] ats = null;
        //     for (String token : dingDingHooks.split(",")) {
        //         // 已添加告警到公共群的不重复发告警
        //         if (noticeService.isWatcherToken(token)) {
        //             publicWatchFlag = false;
        //         }
        //         if (token.contains("&")) {
        //             String [] tokenArr = token.split("&");
        //             if (tokenArr.length >= 2 && StringUtils.isNotBlank(tokenArr[1])) {
        //                 ats = tokenArr[1].substring(1).split("@");
        //             }
        //         } else {
        //             if (StringUtils.isNotBlank(user.getPhone())) {
        //                 ats = new String[]{user.getPhone()};
        //             }
        //         }
        //         noticeService.sendDingding(token, ats, content);
        //     }
        // }
        // if (publicWatchFlag) {
        //     //发送告警到公共群
        //     noticeService.sendDingding(new String[0], content);
        // }
    }

    protected void notice(Cluster cluster, Script script, HttpYarnApp httpYarnApp, String trackingUrls, String errorType) {
        AuthUser user = null;
        String msg;
        // 脚本信息不为null，则直接进行消息的提醒
        if (script != null) {
            user = userService.getById(script.getUid());
            String userName = user.getNickname() != null ? user.getNickname() : user.getUsername();
            // 拼接提醒的内容
            msg = MsgTools.getPlainErrorMsg(cluster.getName(), trackingUrls, userName, script.getName(), errorType);
        } else {
            // 脚本信息为空，那就通过 HttpYarnApp进行中的队列以及clusterId来获得集群对象
            String queue = httpYarnApp.getQueue();
            List<ClusterUser> clusterUsers = clusterUserService.getByClusterIdAndQueue(queue, cluster.getId());
            if (clusterUsers.isEmpty()) {
                // root用户打头的情况
                if (queue.startsWith("root.")) {
                    queue = queue.substring(5);
                    clusterUsers = clusterUserService.getByClusterIdAndQueue(queue, cluster.getId());
                }
            }
            // 指定的集群用户是空的
            if (!clusterUsers.isEmpty()) {
                boolean match = false;
                for (ClusterUser clusterUser : clusterUsers) {
                    // 参数传入的用户与之是匹配的
                    if (httpYarnApp.getUser().equals(clusterUser.getUser())) {
                        user = userService.getById(clusterUser.getUid());
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    // 如果没有匹配，那么就通过集群用户的第一个用户来获得
                    user = userService.getById(clusterUsers.get(0).getUid());
                }
                String userName = user.getNickname() != null ? user.getNickname() : user.getUsername();
                msg = MsgTools.getPlainErrorMsg(cluster.getName(), trackingUrls, userName, httpYarnApp.getName(), errorType);
            } else {
                msg = MsgTools.getPlainErrorMsg(cluster.getName(), trackingUrls, httpYarnApp.getUser(), httpYarnApp.getName(), errorType);
            }
        }
        // if (user != null && StringUtils.isNotBlank(user.getEmail())) {
        //     noticeService.sendEmail(user.getEmail(), msg);
        // }
        // noticeService.sendDingding(new String[0], msg);
    }
}
