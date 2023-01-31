package com.example.private_lesson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.example.private_lesson.model.Teacher;

import java.util.List;

public class TeacherViewModel extends ViewModel {


    private LiveData<List<Teacher>> dataTeacher = Model.instance().getAllTeachers();

    LiveData<List<Teacher>> getData(){
        return dataTeacher;
    }

}

