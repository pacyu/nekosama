package com.pacyu.blog.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Remotes")
public class Remote {

    private String aid;

    private String address;

    public Remote() {}

    public Remote (String aid, String address) {
        this.aid = aid;
        this.address = address;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}