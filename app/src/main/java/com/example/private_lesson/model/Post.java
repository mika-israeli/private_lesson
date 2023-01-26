package com.example.private_lesson.model;

public class Post {

    public String teacherName;
    public String id;

    public String description;
    public String price;
    public Boolean cb;

    public Post(String teacherName, String description, String price, String id, Boolean cb) {
        this.teacherName = teacherName;

        this.description = description;
        this.price = price;
        this.id = id;
        this.cb = cb;
    }
}
