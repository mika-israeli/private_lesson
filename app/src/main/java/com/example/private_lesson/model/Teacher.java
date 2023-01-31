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

public class Teacher {


    @PrimaryKey
    @NonNull
    public String teacherId="";
    public String teacherName="";
    public String avatarUrl="";
    public Long lastUpdated;

    public Teacher(){
    }




    public Teacher(@NonNull String teacherId, String teacherName, String avatarUrl) {
        this.teacherName = teacherName;
        this.avatarUrl = avatarUrl;
        this.teacherId = teacherId;
    }

    static final String NAME = "name";
    static final String ID = "teacherId";
    static final String AVATAR = "avatar";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "teachers_local_last_update";
    static final String COLLECTION = "teachers";

    public static Long getLocalLastUpdate(){
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG",
                Context.MODE_PRIVATE);
        return sp.getLong(LOCAL_LAST_UPDATED,0);
    }

    public static void setLocalLastUpdate(Long lastUpdate){
        SharedPreferences sp = MyApplication.getMyContext().getSharedPreferences("TAG",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(LOCAL_LAST_UPDATED,lastUpdate);
        editor.commit();
    }

    public static Teacher fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        String avatar = (String)json.get(AVATAR);
        Teacher teacher = new Teacher(id,name,avatar);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            teacher.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return teacher;
    }

    public Map<String,Object> toJson(){
        Map<String,Object> json = new HashMap<>();
        json.put(NAME,getTeacherName());
        json.put(ID,getTeacherId());
        json.put(AVATAR,getAvatarUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }
    @NonNull
    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
    public void setTeacherId(@NonNull String teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
