package com.example.private_lesson;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.example.private_lesson.model.Teacher;
import java.util.List;

public class TeacherPostsFragmentViewModel extends ViewModel {

        private LiveData<List<Teacher>> teachers = Model.instance().getAllTeachers();




    private LiveData<List<Post>> allPosts = Model.instance().getAllPosts();


    List<Post> getMyData(List<Post> l, String id) {
        return Model.instance().getMyPosts(l,id);
    }

    Post getPostById(String id) {
        return Model.instance().getPostById2(id,allPosts.getValue());
    }

        LiveData<List<Teacher>> getData()   {
            return teachers;
        }
    public LiveData<List<Post>> getAllPosts() {
        return allPosts;
    }
    public void deletePost(String postPd) {
        allPosts.getValue().remove(getPostById(postPd));
    }



}

