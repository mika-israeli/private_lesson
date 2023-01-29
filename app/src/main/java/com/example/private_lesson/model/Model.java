package com.example.private_lesson.model;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Model {
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());



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

    public void  getAllPosts(GetAllPostsListener callback) {
        executor.execute(() -> {
            List<Post> data = localDb.postDao().getAll();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(() -> {
                callback.onComplete(data);
            });
        });
    }

public interface AddPostListener {
        void onComplete();
    }
    public void addPost(Post post, AddPostListener listener) {
        executor.execute(()->{
            localDb.postDao().insertAll(post);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(()->{
                listener.onComplete();
            });
        });
    }


}
