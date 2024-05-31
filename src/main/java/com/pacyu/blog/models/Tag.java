package com.pacyu.blog.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Tags")
public class Tag {
    @Id
    private String id;

    private String tag;

    public Tag() {}

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}