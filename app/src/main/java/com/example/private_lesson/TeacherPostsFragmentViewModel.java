package com.example.private_lesson;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.example.private_lesson.model.Teacher;
import java.util.List;

public class TeacherPostsFragmentViewModel extends ViewModel {

        private LiveData<List<Teacher>> teachers = Model.instance().getAllTeachers();


    List<Post> getMyData(List<Post> l, String id) {
        return Model.instance().getMyPosts(l,id);
    }

        LiveData<List<Teacher>> getData(){
            return teachers;
        }




}

