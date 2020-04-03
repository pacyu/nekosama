package com.nekosamahe.blog.helper;

import java.util.*;

public class Master {

    private String username;

    private Date replyDate;

    private String content;

    public Master() {}

    public Master(String username, Date replyDate, String content) {
        this.username = username;
        this.replyDate = replyDate;
        this.content = content;
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