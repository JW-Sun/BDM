package com.jw.bigwhalemonitor.controller.admin.auth;

import com.jw.bigwhalemonitor.common.pojo.Msg;
import com.jw.bigwhalemonitor.controller.BaseController;
import com.jw.bigwhalemonitor.entity.AuthResource;
import com.jw.bigwhalemonitor.entity.AuthRole;
import com.jw.bigwhalemonitor.entity.AuthRoleResource;
import com.jw.bigwhalemonitor.entity.AuthUser;
import com.jw.bigwhalemonitor.entity.AuthUserRole;
import com.jw.bigwhalemonitor.service.ResourceService;
import com.jw.bigwhalemonitor.service.RoleResourceService;
import com.jw.bigwhalemonitor.service.RoleService;
import com.jw.bigwhalemonitor.service.UserRoleService;
import com.jw.bigwhalemonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserRoleService userRoleService;

    private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    @GetMapping("/user/getall.api")
    public Msg getAll() {
        List<AuthUser> userList = userService.getAllUser();
        for (AuthUser authUser : userList) {
            authUser.setPassword(null);
        }
        return success("获得所有用户", userList);
    }

    // 获取所有资源文件
    @GetMapping("/resource/list.api")
    public Msg getResource() {
        List<AuthResource> all = resourceService.getAll();
        return success("获得所有资源文件", all);
    }

    // 资源文件的保存与更新
    @PostMapping("/resource/save.api")
    public Msg saveResource(@RequestBody AuthResource resource) {
        if (resource.getUrl() == null) {
            resource.setUrl("");
        }
        if (resource.getId() == null) {
            AuthResource byCode = resourceService.getByCode(resource.getCode());
            if (byCode != null) {
                return failed("已经存在");
            }
        }
        AuthResource save = resourceService.save(resource);
        return success(save);
    }

    // 资源文件的删除
    @PostMapping("/resource/delete.api")
    public Msg deleteResource(String id) {
        AuthResource byId = resourceService.getById(id);
        if (byId != null) {
            int delete = resourceService.delete(id);
        }
        return success();
    }


    // 权限管理-角色管理
    @GetMapping("/role/list.api")
    public Msg getRoleList() {
        List<AuthRole> roles = roleService.getAll();
        for (AuthRole role : roles) {
            List<AuthRoleResource> byRole = roleResourceService.getByRole(role.getCode());
            List<String> resources = new ArrayList<>();
            if (byRole.size() > 0) {
                for (AuthRoleResource authRoleResource : byRole) {
                    resources.add(authRoleResource.getResource());
                }
            }
            role.setResources(resources);
        }
        return success(roles);
    }

    // 对系统的角色进行管理
    @PostMapping("/role/save.api")
    public Msg saveRole(@RequestBody AuthRole role) {
        if (role.getId() == null) {
            List<AuthRole> byCode = roleService.getByCode(role.getCode());
            if (byCode.size() > 0) {
                return failed("role has existed");
            }
        }
        // 保存属性之前先将之前的进行删除
        AuthRole save = roleService.save(role);
        return success(save);
    }

    @PostMapping("/role/delete.api")
    public Msg deleteRole(String id) {
        roleService.deleteById(id);
        return success();
    }

    // 查所有的user列表
    @GetMapping("/user/list.api")
    public Msg getAllUser() {
        List<AuthUser> allUser = userService.getAllUser();
        if (allUser != null && allUser.size() > 0) {
            for (AuthUser user : allUser) {
                List<String> roles = new ArrayList<>();
                String username = user.getUsername();
                List<AuthUserRole> byUsername = userRoleService.getByUsername(username);
                if (!byUsername.isEmpty()) {
                    for (AuthUserRole authUserRole : byUsername) {
                        roles.add(authUserRole.getRole());
                    }
                }
                user.setRoles(roles);
            }
        }
        return success(allUser);
    }

    // 针对user进行保存
    @PostMapping("/user/save.api")
    public Msg userSave(@RequestBody AuthUser user) {
        if (user.getId() == null) {
            String username = user.getUsername();
            List<AuthUser> userByUsername = userService.getUserByUsername(username);
            if (!userByUsername.isEmpty()) {
                return failed("username exist.");
            }
        }
        userService.save(user);
        return success("save suc");
    }


}
