package com.example.private_lesson.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
    }


    //    public void getAllStudents(Model.GetAllPostsListenerListener callback){
//        db.collection(Post.COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<Post> list = new LinkedList<>();
//                if (task.isSuccessful()){
//                    QuerySnapshot jsonsList = task.getResult();
//                    for (DocumentSnapshot json: jsonsList){
//                     Post post =Post.fromJson(json.getData());
//                        list.add(post);
//                    }
//                }
//                callback.onComplete(list);
//            }
//        });
//    }
//
//    public void addStudent(Post post, Model.AddPostListener listener) {
//        db.collection(Post.COLLECTION).document(post.getId()).set(post.toJson())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        listener.onComplete();
//                    }
//                });
//    }
    public void getAllPosts(Model.GetAllPostsListener callback) {
        db.collection("lesssons").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> list = new LinkedList<>();
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        Post post = Post.fromJson(json.getData());
                        list.add(post);
                    }
                }
                callback.onComplete(list);
            }
        });


    }

    public void addPost(Post post, Model.AddPostListener listener) {
        Map<String, Object> json = new HashMap<>();
        json.put("name", post.getTeacherName());
        json.put("id", post.getId());
        json.put("description", post.getDescription());
        json.put("price", post.getPrice());
        json.put("avatarUrl", post.getAvatarUrl());
        json.put("cb", post.getCb());

        db.collection("lesssons").document(post.getId()).set(post.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        });
    }

         void uploadImage (String name, Bitmap bitmap, Model.UploadImageListener listener){
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

