package com.example.private_lesson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.private_lesson.databinding.FragmentAddTeacherBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Teacher;

public class AddTeacherFragment extends Fragment {

FragmentAddTeacherBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
        }

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {


            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

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
                    binding.avatarImg.setImageURI(result);
                    isAvatarSelected = true;

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTeacherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.saveButton.setOnClickListener(view1->{
            String id =  Model.instance().getAuth().getCurrentUser().getUid().toString();
            String name = binding.nameEt.getText().toString();
            Teacher teacher = new Teacher(id, name,"");
            if(isAvatarSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(id,bitmap ,URL->{
                    if(URL != null) {
                        teacher.setAvatarUrl(URL);
                    }
                    Model.instance().addTeacher(teacher,(unused)->{
//                        Navigation.findNavController(view1).popBackStack();
                        });

                });
            }
            else{
                Model.instance().addTeacher(teacher,(unused)->{
//                Navigation.findNavController(view1).popBackStack();
                });
            }
            Model.instance().refreshAllTeachers();
            Model.instance().refreshAllPosts();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        });
        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });
        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;

    }
}