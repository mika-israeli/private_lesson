package com.example.private_lesson.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }
    private Model() {
        for(int i = 0; i < 10; i++) {
          addPost(new Post("teacherName" + i, "description" + i, "price" + i, "id" + i, false));
        }
    }

    List<Post> allPosts=new LinkedList<>();
    public List<Post> getAllPosts() {
        return allPosts;
    }
    public void addPost(Post post) {
        allPosts.add(post);
    }



}
