package com.pacyu.blog.helper;

import java.util.*;

public class Replier {

    private String replierName;

    private String username;

    private Date replyDate;

    private String content;

    public Replier() {}

    public Replier(String replierName, String username, Date replyDate, String content) {
        this.replierName = replierName;
        this.username = username;
        this.replyDate = replyDate;
        this.content = content;
    }

    public String getReplierName() {
        return replierName;
    }

    public String getUsername() {
        return username;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public String getContent() {
        return content;
    }

    public void setReplierName(String replierName) {
        this.replierName = replierName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public void setContent(String content) {
        this.content = content;
    }
}