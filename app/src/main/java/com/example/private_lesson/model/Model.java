package com.example.private_lesson.model;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.google.firebase.firestore.util.Listener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Model {
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();


    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }

    private Model() {


    }

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public interface GetAllPostsListener {
        void onComplete(List<Post> data);
    }

    public void getAllPosts(GetAllPostsListener callback) {
        firebaseModel.getAllPosts(callback);
    }

    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post post, AddPostListener listener) {
        firebaseModel.addPost(post, listener);
    }

    public interface UploadImageListener {
        void onComplete(String url);
    }

    public void uploadImage(String name, Bitmap bitmap,UploadImageListener listener) {

firebaseModel.uploadImage(name,bitmap,listener);
    }
}
