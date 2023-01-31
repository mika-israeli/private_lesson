package com.example.private_lesson.model;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.private_lesson.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id="";

    public String getUserId() {
        return userId;
    }

    public String userId="";
    public String teacherName="";
    public String description="";
    public String price="";
    public String avatarUrl="";
    public Boolean cb=false;
    public Long lastUpdated;

    public Post(){
    }
    public Post(String teacherName, String description,
                  String price,String id, Boolean cb,String avatarUrl, String userId) {
        this.teacherName = teacherName;
        this.description = description;
        this.price = price;
        this.id = id;
        this.cb = cb;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
    }


    static final String NAME = "name";
    static final String ID = "id";

    static final String USER = "user";
    static final String DESCRIPTION = "description";
    static final String PRICE = "price";
    static final String AVATAR = "avatar";
    static final String CB = "cb";
    static final String COLLECTION = "lessons";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        String description = (String)json.get(DESCRIPTION);
        String price = (String)json.get(PRICE);
        String avatar = (String)json.get(AVATAR);
        Boolean cb = (Boolean) json.get(CB);
        String userId = (String)json.get(USER);
        Post post = new Post(name,description,price,id,cb,avatar,userId);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            post.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return post;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }




    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USER,geyUserId());
        json.put(NAME, getTeacherName());
        json.put(DESCRIPTION, getDescription());
        json.put(PRICE, getPrice());
        json.put(AVATAR, getAvatarUrl());
        json.put(CB, getCb());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }



    public void setId(@NonNull String id) {
        this.id = id;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTeacherName(String name) {
        this.teacherName = name;
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

    @NonNull
    public String getId() {
        return id;
    }
    private String geyUserId() {
        return userId;
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
