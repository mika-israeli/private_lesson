package com.example.private_lesson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {


    private LiveData<List<Post>> data = Model.instance().getAllPosts();

     LiveData<List<Post>> getData(){
        return data;
    }

}
