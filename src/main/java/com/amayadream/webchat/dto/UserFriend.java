package com.amayadream.webchat.dto;


public class UserFriend {
    private String id;

    private String userId;

    private String userName;

    private Byte onLine;

    private Byte status;

    private Byte logicalDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getOnLine() {
        return onLine;
    }

    public void setOnLine(Byte onLine) {
        this.onLine = onLine;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getLogicalDel() {
        return logicalDel;
    }

    public void setLogicalDel(Byte logicalDel) {
        this.logicalDel = logicalDel;
    }
}