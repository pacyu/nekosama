package com.nekosamahe.blog.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest; 
import java.util.*;
import java.text.*;
import java.math.BigInteger;


@Document(collection = "Archives")
public class Archive {

    @Id
    private String id;

    private String title;
    
    private String introduction;

    private String content;

    private String cover;
    
    private ArrayList<String> category;

    private Date createDate;

    private Date releaseDate;

    private Date updateDate;

    private String author;

    private String hashString;
    
    private long editCount;

    private long likes;

    private long views;

    private long reviews;

    private Boolean isRemove;

    private Boolean isDraft;

    private ArrayList<String> tag;

    public Archive() {

    }
    
    public Archive(String title, String introduction, String content, String cover, ArrayList<String> category,
        Date createDate, Date releaseDate, Date updateDate, String author,
        long editCount, long likes, long views, long reviews, Boolean isRemove, Boolean isDraft, ArrayList<String> tag) {
         this.title = title;
         this.introduction = introduction;
         this.content = content;
         this.cover = cover;
         this.category = category;
         this.createDate = createDate;
         this.releaseDate = releaseDate;
         this.updateDate = updateDate;
         this.author = author;
         this.editCount = editCount;
         this.likes = likes;
         this.views = views;
         this.reviews = reviews;
         this.isRemove = isRemove;
         this.isDraft = isDraft;
         this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getContent() {
        return content;
    }

    public String getCover() {
        return cover;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getHashString()
    {
        return hashString;
    }

    public long getEditCount() {
        return editCount;
    }

    public long getLikes() {
        return likes;
    }

    public long getViews() {
        return views;
    }

    public long getReviews() {
        return reviews;
    }

    public Boolean getIsRemove() {
        return isRemove;
    }

    public Boolean getIsDraft() {
        return isDraft;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    public void setEditCount(long editCount) {
        this.editCount = editCount;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public void setReviews(long reviews) {
        this.reviews = reviews;
    }

    public void setIsRemove(Boolean isRemove) {
        this.isRemove = isRemove;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.format(
            "Archive[id=%s, title='%s', introduction='%s', author='%s']",
            id, title, introduction, author);
    }
    
    public String genMd5() {
        try {
            String text = title + createDate + id;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);
            while (hashText.length() < 32) { 
                hashText = "0" + hashText; 
            } 
            return hashText;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}