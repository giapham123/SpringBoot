package com.example.demo.models;

import java.time.LocalDateTime;

public class UserSessionInfo {
    private String username;
    private String ipAddress;
    private LocalDateTime LoginTime;

    // Constructor
    public UserSessionInfo(String username, String ipAddress, LocalDateTime LoginTime) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.LoginTime = LoginTime;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        LoginTime = loginTime;
    }
}
