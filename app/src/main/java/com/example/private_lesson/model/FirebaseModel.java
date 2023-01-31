package com.example.private_lesson.model;


import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel{
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseUser user;

    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    public void signUp(String email, String password, Model.Listener<String> listener){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("TAG", "signUp: success");
                    user = auth.getCurrentUser();
                    listener.onComplete(user.getUid());
                }
                else{
                    Log.d("TAG", "signUp: failed");
                    listener.onComplete("");
                }
            }
        });
    }

    public void login(String email, String password, Model.Listener<Boolean> listener){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("TAG", "login: success");
                    user = auth.getCurrentUser();
                    listener.onComplete(true);
                }
                else{
                    Log.d("TAG", "login: failed");
                    listener.onComplete(false);
                }
            }
        });
    }

    public void getAllPostsSince(Long since,Model.Listener<List<Post>> callback){
        db.collection(Post.COLLECTION).whereGreaterThanOrEqualTo(Post.LAST_UPDATED,new Timestamp(since,0)).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> list = new LinkedList<>();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList){
                      Post post = Post.fromJson(json.getData());
                        list.add(post);
                    }
                }
                callback.onComplete(list);
            }
        });
    }
    public void getAllTeachersSince(Long since,Model.Listener<List<Teacher>> callback){
        db.collection(Teacher.COLLECTION).whereGreaterThanOrEqualTo(Teacher.LAST_UPDATED,new Timestamp(since,0)).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Teacher> list = new LinkedList<>();
                        if (task.isSuccessful()){
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json: jsonsList){
                                Teacher teacher = Teacher.fromJson(json.getData());
                                list.add(teacher);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    public void addPost(Post post, Model.Listener<Void> listener) {
        db.collection(Post.COLLECTION).document(post.getId()).set(post.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }
    public void addTeacher(Teacher teacher, Model.Listener<Void> listener) {
        db.collection(Teacher.COLLECTION).document(teacher.teacherId).set(teacher.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void getPostById(String id, Model.Listener<Post> listener){
        db.collection(Post.COLLECTION).whereEqualTo(Post.ID,id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Post post = new Post();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList){
                        post = Post.fromJson(json.getData());

                    }
                }
                listener.onComplete(post);
            }
        });
    }

    public void getTeacherById(String id, Model.Listener<Teacher> listener){
        db.collection(Teacher.COLLECTION).whereEqualTo(Teacher.ID,id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               Teacher teacher= new Teacher();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json: jsonsList){
                        teacher = Teacher.fromJson(json.getData());

                    }
                }
                listener.onComplete(teacher);
            }
        });
    }


    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });

    }




}
