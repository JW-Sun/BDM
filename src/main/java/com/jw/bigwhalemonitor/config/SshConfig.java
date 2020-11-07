package com.jw.bigwhalemonitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@ConfigurationProperties(prefix = "big-whale.ssh")
public class SshConfig {

    private String user;

    private String password;

    private int connectTimeout = 5000;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        Assert.notNull(user, "ssh user must be present");
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
