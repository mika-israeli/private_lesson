package com.example.private_lesson;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.private_lesson.databinding.FragmentEditAboutBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Teacher;
import com.squareup.picasso.Picasso;

public class EditAboutFragment extends Fragment {

    FragmentEditAboutBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    String userId;
    String avatar;


    public EditAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString("userId");

        cameraLauncher = registerForActivityResult(new
                        ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if (result != null) {
                            binding.avatarImg.setImageBitmap(result);
                            isAvatarSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new
                        ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            binding.avatarImg.setImageURI(result);
                            isAvatarSelected = true;
                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userId = EditAboutFragmentArgs.fromBundle(getArguments()).getUserId();
        Log.d("TAG", "onCreateView: " + userId);




        return null;}
//        binding = FragmentEditAboutBinding.inflate(inflater, container, false);
//        View view = binding.getRoot();
//
//        Model.instance().getTeacherById(userId, teacher -> {
//            avatar = teacher.getAvatarUrl();
//            binding.editId.setText(teacher.getTeacherId());
//            binding.nameEt.setText(teacher.getTeacherName());
//            if (!teacher.getAvatarUrl().equals("")) {
//                Picasso.get().load(teacher.getAvatarUrl()).placeholder(R.drawable.avatar).into(binding.avatarImg);
//            } else {
//                binding.avatarImg.setImageResource(R.drawable.avatar);
//            }
//        });
//
//        binding.saveBtn.setOnClickListener(v -> {
//            String id = binding.editId.getText().toString();
//            String name = binding.nameEt.getText().toString();
//            Teacher teacher = new Teacher(id, name, avatar);
//            if (isAvatarSelected) {
//
//            }
//        });
//    }

}

