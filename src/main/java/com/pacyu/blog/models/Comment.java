package com.pacyu.blog.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "Comments")
public class Comment {
    @Id
    private String id;

    private String aid;

    private String uid;

    private String toUid;

    private String content;

    private long floor;

    private long layer;

    private long relate;

    private String relateId;

    private Date postDate;

    private Date updateDate;

    private Boolean isEdit;

    public Comment() {}

    public Comment(String aid, String uid, String content, long floor, long layer, long relate, Date postDate) {
        this.aid = aid;
        this.uid = uid;
        this.content = content;
        this.floor = floor;
        this.layer = layer;
        this.postDate = postDate;
        this.relate = relate;
        this.isEdit = false;
    }

    public String getId() {
        return id;
    }

    public String getAid() {
        return aid;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    public String getToUid() {
        return toUid;
    }

    public long getFloor() {
        return floor;
    }

    public long getLayer() {
        return layer;
    }

    public long getRelate() {
        return relate;
    }

    public String getRelateId() {
        return relateId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public void setLayer(long layer) {
        this.layer = layer;
    }

    public void setRelate(long relate) {
        this.relate = relate;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }
}