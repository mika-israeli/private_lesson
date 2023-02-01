package com.example.private_lesson;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

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
                new ActivityResultCallback<Uri>() {             @Override
                    public void onActivityResult(Uri result) {
                                binding.avatarImg.setImageURI(result);
                            isAvatarSelected = true;

                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit your profile");
        userId = getArguments().getString("userId");
        Log.d("TAG", "onCreateView: " + userId);
        binding = FragmentEditAboutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Model.instance().getTeacherById(userId, teacher -> {
            avatar = teacher.getAvatarUrl();
            binding.nameEt.setText(teacher.getTeacherName());
            if (!teacher.getAvatarUrl().equals("")) {
                Picasso.get().load(teacher.getAvatarUrl()).into(binding.avatarImg);
            } else {
                binding.avatarImg.setImageResource(R.drawable.avatar);
            }

        });

        binding.saveButton.setOnClickListener(view1 -> {
            String name = binding.nameEt.getText().toString();
            Teacher teacher = new Teacher(userId, name, avatar);
            if (isAvatarSelected) {
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(userId, bitmap, url -> {
                    if (url != null) {
                        teacher.setAvatarUrl(url);
                    }
                    Model.instance().addTeacher(teacher, (unused) -> {
                       Navigation.findNavController(view).navigateUp();


                    });
                });
            }
            else
                {
                    Model.instance().addTeacher(teacher, (unused) -> {
                        //back to profile and refresh
                        Navigation.findNavController(view).navigateUp() ;

                    });
                }

        });

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });
        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

return view;

    }


}

