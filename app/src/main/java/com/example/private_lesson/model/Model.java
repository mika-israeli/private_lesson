package com.example.private_lesson.model;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    final public MutableLiveData<LoadingState> EventTeachersListLoadingState =
            new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Post>> postsList;
    private LiveData<List<Post>> postsListByTeacher;
    private LiveData<List<Teacher>> teachersList;
    public LiveData<List<Post>> getAllPosts() {
        if(postsList==null){
            postsList=localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;

    }
    public LiveData<List<Teacher>> getAllTeachers() {
        if(teachersList==null){
            teachersList=localDb.teacherDao().getAll();
            refreshAllPosts();
        }
        return teachersList;

    }
    public List<Post> getMyPosts(List<Post> all, String id){

        List<Post> mine=new LinkedList<>();
        for(Post p: all)
        {
            if(p.getUserId().equals(id))
            {mine.add(p);
            }
        }
        return mine;
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
    public void refreshAllMyPosts() {
        EventTeachersListLoadingState.setValue(LoadingState.LOADING);
        Long localLocalLastUpdate = Teacher.getLocalLastUpdate();
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
    public void refreshAllTeachers() {
        EventTeachersListLoadingState.setValue(LoadingState.LOADING);
        Long localLocalLastUpdate = Teacher.getLocalLastUpdate();
        firebaseModel.getAllTeachersSince(localLocalLastUpdate,List->{
            executor.execute(()->{
                Log.d("TAG", "firebase return: "+List.size());
                Long time=localLocalLastUpdate;
                for(Teacher teacher:List){
                    localDb.teacherDao().insertAll(teacher);
                    if(time<teacher.getLastUpdated()){
                        time=teacher.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Teacher.setLocalLastUpdate(time);
                EventTeachersListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });


    }


    public void signUp(String Email, String Password, Listener<String> listener) {
        firebaseModel.signUp(Email,Password,listener);
    }
    public void signIn(String Email, String Password, Listener<Boolean> listener) {
        firebaseModel.login(Email,Password,listener);
    }

    public FirebaseAuth getAuth() {
        return firebaseModel.auth;
    }
    public FirebaseUser getFireUser() {
        return firebaseModel.user;
    }
    public void addPost(Post po, Listener<Void> listener){
        firebaseModel.addPost(po,(Void)->{
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void addTeacher(Teacher teacher, Listener<Void> listener){
        firebaseModel.addTeacher(teacher,(Void)->{
            refreshAllTeachers();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

    public void getTeacherById(String id, Listener<Teacher> listener) {
        executor.execute(() -> {
                    Teacher teacher = localDb.teacherDao().getTeacherById(id);
                    mainHandler.post(() -> {
                        listener.onComplete(teacher);
                    });
                }
        );
    }
    public void getPostById(String id, Listener<Post> listener) {
        executor.execute(() -> {
                    Post po = localDb.postDao().getPostById(id);
                    mainHandler.post(() -> {
                        listener.onComplete(po);
                    });
                }
        );
    }

    public Post getPostById2(String id, List<Post> list) {
           for(Post p:list)
            {
                if(p.getId().equals(id))
                {
                    return p;
                }
            }
           return null;

    }

    public LiveData<List<Post>> getAllPostsByTeacher() {
        if(postsListByTeacher==null){
            postsListByTeacher=localDb.postDao().getAllPostsByTeacher(firebaseModel.auth.getUid());

        }
        return postsListByTeacher;

    }

}
