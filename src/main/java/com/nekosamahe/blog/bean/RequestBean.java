package com.nekosamahe.blog.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RequestBean {
    private String type;
    private String title;
    private String content;
    private String introduction;
    private String cover;
    private ArrayList<String> categories;
    private ArrayList<String> tags;
    private Boolean isRelease;

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getCover() {
        return cover;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Boolean getRelease() {
        return isRelease;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setRelease(Boolean isRelease) {
        this.isRelease = isRelease;
    }
}
