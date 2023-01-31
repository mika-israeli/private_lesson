package com.example.private_lesson.model;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

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

    public enum LoadingState{
        LOADING,NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventPostsListLoadingState =
            new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Post>> postsList;
    public LiveData<List<Post>> getAllPosts() {
        if(postsList==null){
            postsList=localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;

    }
    public void refreshAllPosts() {
        EventPostsListLoadingState.setValue(LoadingState.LOADING);
        Long localLocalLastUpdate = Post.getLocalLastUpdate();
        firebaseModel.getAllPostsSince(localLocalLastUpdate,List->{
            executor.execute(()->{
                Log.d("TAG", "firebase return: "+List.size());
                Long time=localLocalLastUpdate;
                for(Post post:List){
                    localDb.postDao().insertAll(post);
                    if(time<post.getLastUpdated()){
                        time=post.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Post.setLocalLastUpdate(time);
                EventPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
                });


    }

    public void signUp(String Email, String Password, Listener<String> listener) {
        firebaseModel.signUp(Email,Password,listener);
    }
    public void logIn(String Email, String Password, Listener<Boolean> listener) {
        firebaseModel.login(Email,Password,listener);
    }

    public FirebaseAuth getAuth() {
        return firebaseModel.auth;
    }

    public void addPost(Post po, Listener<Void> listener){
        firebaseModel.addPost(po,(Void)->{
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

}
