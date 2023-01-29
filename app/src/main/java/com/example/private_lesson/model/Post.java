package com.example.private_lesson.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
