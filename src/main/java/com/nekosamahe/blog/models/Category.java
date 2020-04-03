package com.nekosamahe.blog.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Categories")
public class Category {
    @Id
    private String id;

    private String kind;

    private String bgImage;

    public Category() {}

    public Category(String kind) {
        this.kind = kind;
    }

    public Category(String kind, String bgImage) {
        this.kind = kind;
        this.bgImage = bgImage;
    }

    public String getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }
    
    public String getBgImage() {
        return bgImage;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }
}