package com.example.private_lesson.model;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }
    public void getAllPosts(Listener<List<Post>> callback) {
        Long localLocalLastUpdate = Post.getLocalLastUpdate();
        firebaseModel.getAllPostsSince(localLocalLastUpdate,List->{
            executor.execute(()->{
                Long time=localLocalLastUpdate;
                for(Post post:List){
                    localDb.postDao().insertAll(post);
                    if(time<post.getLastUpdated()){
                        time=post.getLastUpdated();
                    }
                }
                Post.setLocalLastUpdate(time);
                List<Post> complete = localDb.postDao().getAll();
                mainHandler.post(()->{
                    callback.onComplete(complete);
                });
            });
                });


    }
    public void addPost(Post po, Listener<Void> listener){
        firebaseModel.addPost(po,listener);

    }

    public void uploadImage(String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

}
