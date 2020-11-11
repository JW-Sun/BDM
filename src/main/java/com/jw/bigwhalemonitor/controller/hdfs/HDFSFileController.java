package com.jw.bigwhalemonitor.controller.hdfs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.jw.bigwhalemonitor.common.pojo.FileStatus;
import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.entity.Cluster;
import com.jw.bigwhalemonitor.security.LoginUser;
import com.jw.bigwhalemonitor.service.cluster.ClusterService;
import com.jw.bigwhalemonitor.util.WebHdfsUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hdfs")
public class HDFSFileController extends BaseController {

    @Autowired
    private ClusterService clusterService;

    /***
     *  1. 将任务的执行文件上传到HDFS上 。 先围绕path进行讨论
     */
    @PostMapping("upload")
    public Msg upload(MultipartFile multipartFile,
                      @RequestParam(required = false) String clusterId,
                      @RequestParam(required = false) String path) {
        Cluster cluster = null;
        LoginUser currentUser = getCurrentUser();
        // 如果path是空
        if (StringUtils.isBlank(path)) {
            // 先找到默认的集群
            Cluster defaultCluster = clusterService.getDefaultCluster();
            if (defaultCluster == null) {
                if (StringUtils.isBlank(clusterId)) {
                    return failed("没有指定clusterId");
                } else {
                    cluster = clusterService.getById(clusterId);
                }
            }
            path = cluster.getFsDir() + "/" + currentUser.getUsername();
        } else {
            // path的格式就是统一的 / 打头的
            path = regularPath(path);
            Cluster clusterById = clusterService.getById(clusterId);
            cluster = clusterById;
            if (currentUser.isRoot()) {
                path = clusterById.getFsDir() + path;
            } else {
                // 非root用户需要加上针对user的标识
                path = clusterById.getFsDir() + "/" + currentUser.getUsername() + path;
            }
        }
        boolean successUpload = WebHdfsUtils.upload(multipartFile, cluster.getFsWebhdfs(), path, cluster.getFsUser(), currentUser.getUsername());
        if (successUpload) {
            String fsDefaultFs = cluster.getFsDefaultFs();
            if (fsDefaultFs.endsWith("/")) {
                fsDefaultFs = fsDefaultFs.substring(0, fsDefaultFs.length() - 1);
            }
            String retName = fsDefaultFs + path + "/" + multipartFile.getOriginalFilename();
            return success("上传成功的文件：" + retName);
        } else {
            return failed("上传文件直接失败");
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downFile(
            HttpServletResponse response,
            @RequestParam String clusterId,
            @RequestParam String path) throws UnsupportedEncodingException {
        path = regularPath(path);
        String name = path.substring(path.lastIndexOf('/') + 1);
        response.setContentType("application/force-download;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(name.getBytes(), "ISO8859-1"));
        Cluster cluster = clusterService.getById(clusterId);
        LoginUser loginUser = getCurrentUser();
        if (loginUser.isRoot()) {
            path = cluster.getFsDir() + path;
        } else {
            path = cluster.getFsDir() + "/" + loginUser.getUsername() + path;
        }
        WebHdfsUtils.download(response, cluster.getFsWebhdfs(), path, loginUser.getUsername());
    }

    /***
     *  删除一个集群上的路径中的执行工作Jar包
     * @param clusterId
     * @param path
     * @return
     */
    @RequestMapping(value = "/delete.api", method = RequestMethod.POST)
    public Msg delete(
            @RequestParam String clusterId,
            @RequestParam String path) {
        path = regularPath(path);
        Cluster cluster = clusterService.getById(clusterId);
        LoginUser loginUser = getCurrentUser();
        int result;
        if (loginUser.isRoot()) {
            path = cluster.getFsDir() + path;
            result = WebHdfsUtils.delete(cluster.getFsWebhdfs(), path, cluster.getFsUser());
        } else {
            path = cluster.getFsDir() + '/' + loginUser.getUsername() + path;
            result = WebHdfsUtils.delete(cluster.getFsWebhdfs(), path, loginUser.getUsername());
        }
        if (result != 200) {
            if (result == 403) {
                return failed("无操作权限");
            } else {
                return failed();
            }
        }
        return success();
    }



    @RequestMapping(value = "/list.api", method = RequestMethod.GET)
    public Msg list(
            @RequestParam String clusterId,
            @RequestParam String path) {
        // 将路径转化为"/path/asd" 这种路径，有开头
        path = regularPath(path);
        Cluster cluster = clusterService.getById(clusterId);
        LoginUser loginUser = getCurrentUser();
        if (loginUser.isRoot()) {
            path = cluster.getFsDir() + path;
        } else {
            path = cluster.getFsDir() + "/" + loginUser.getUsername() + path;
        }
        JSONArray fileStatuses = WebHdfsUtils.list(cluster.getFsWebhdfs(), path, loginUser.isRoot() ? cluster.getFsUser() : loginUser.getUsername());
        List<FileStatus> dtos = new ArrayList<>();
        if (fileStatuses != null) {
            String fsDefaultFs = cluster.getFsDefaultFs();
            if (fsDefaultFs.endsWith("/")) {
                // 让fsDefaultFs的末尾是不带符号的，分隔符都是前置放置
                fsDefaultFs = fsDefaultFs.substring(0, fsDefaultFs.length() - 1);
            }
            String hdfsPathPrefix = fsDefaultFs + path + "/";
            for (Object fileStatus : fileStatuses) {
                FileStatus fileStatusDto = FileStatus.builder()
                        .id(UUID.randomUUID().toString())
                        .name(((JSONObject) fileStatus).getString("pathSuffix"))
                        .path(hdfsPathPrefix + ((JSONObject) fileStatus).getString("pathSuffix"))
                        .length(((JSONObject) fileStatus).getLongValue("length"))
                        .isdir("DIRECTORY".equals(((JSONObject) fileStatus).getString("type")))
                        .modification_time(new Date(((JSONObject) fileStatus).getLongValue("modificationTime")))
                        .permission(((JSONObject) fileStatus).getString("permission"))
                        .owner(((JSONObject) fileStatus).getString("owner"))
                        .group(((JSONObject) fileStatus).getString("group"))
                        .build();
                dtos.add(fileStatusDto);
            }
        }
        return success(dtos);
    }




    private String regularPath(String path) {
        // 转变为 /开头无结尾的情况
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

}



