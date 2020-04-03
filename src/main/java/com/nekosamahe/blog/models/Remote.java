package com.nekosamahe.blog.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Remotes")
public class Remote {
    
    private String address;

    public Remote() {}

    public Remote (String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}