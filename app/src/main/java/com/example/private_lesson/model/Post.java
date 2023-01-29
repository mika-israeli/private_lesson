package com.example.private_lesson.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity

public class Post {
    @PrimaryKey
    @NonNull
    public String id="";
    public String teacherName="";
    public String description="";
    public String price="";
    public String avatarUrl="";
    public Boolean cb=false;

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCb(Boolean cb) {
        this.cb = cb;
    }

    public Post() {

    }

    public String getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Boolean getCb() {
        return cb;
    }



    public Post(String teacherName, String description, String price, String id, Boolean cb, String avatarUrl) {
        this.teacherName = teacherName;
        this.description = description;
        this.price = price;
        this.id = id;
        this.cb = cb;
        this.avatarUrl = avatarUrl;
    }

    static final String NAME = "name";
    static final String ID = "id";
    static final String DESCRIPTION = "description";
    static final String PRICE = "price";
    static final String AVATAR = "avatar";
    static final String CB = "cb";
    static final String COLLECTION = "lesssons";

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        String description = (String)json.get(DESCRIPTION);
        String price = (String)json.get(PRICE);
        String avatar = (String)json.get(AVATAR);
        Boolean cb = (Boolean) json.get(CB);
        Post post = new Post(name,description,price,id,cb,avatar);
        return post;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getTeacherName());
        json.put(DESCRIPTION, getDescription());
        json.put(PRICE, getPrice());
        json.put(AVATAR, getAvatarUrl());
        json.put(CB, getCb());
        return json;
    }

}
