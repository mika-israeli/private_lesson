package com.example.private_lesson.model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;
@Dao

public interface PostDao {

    @Query("select * from Post")
    LiveData<List<Post>> getAll();

        @Query("select * from Post where id = :id")
        Post getPostById(String id);

        @Query("select * from Post where userId=:userId order by lastUpdated desc" )
        LiveData<List<Post>> getAllPostsByTeacher(String userId);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(Post... posts);

        @Delete
        void delete(Post post);



}
