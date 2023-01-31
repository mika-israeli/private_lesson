package com.example.private_lesson.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;
@Dao
public interface TeacherDao {
    @Query("select * from Teacher")
    LiveData<List<Teacher>> getAll();

    @Query("select * from Teacher where teacherId = :teacherId")
    Teacher getTeacherById(String teacherId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Teacher... teachers);

    @Delete
    void delete(Teacher teacher);
}
