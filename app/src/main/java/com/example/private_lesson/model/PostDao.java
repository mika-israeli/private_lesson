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

        @Query("select * from Post where id = :PostId")
        Post getPostById(String PostId);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(Post... students);

        @Delete
        void delete(Post student);



}
