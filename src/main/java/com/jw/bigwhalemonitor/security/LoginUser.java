package com.jw.bigwhalemonitor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginUser extends User {

    private final String id;

    private final boolean root;

    private Map<String, String> resource = new HashMap<>();


    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String id, boolean root) {
        super(username, password, authorities);
        this.id = id;
        this.root = root;
    }

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String id, boolean root) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.root = root;
    }

    // 暂时不知道check啥
    public boolean check(String code) {
        if (root) {
            return true;
        }
        for (String s : resource.keySet()) {
            if (code.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public boolean isRoot() {
        return root;
    }

    public Map<String, String> getResource() {
        return resource;
    }

    public void setResource(Map<String, String> resource) {
        this.resource = resource;
    }
}
