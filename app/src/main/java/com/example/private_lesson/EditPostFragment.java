package com.example.private_lesson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.private_lesson.databinding.FragmentEditAboutBinding;
import com.example.private_lesson.databinding.FragmentEditPostBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.example.private_lesson.model.Teacher;
import com.squareup.picasso.Picasso;

public class EditPostFragment extends Fragment {

    FragmentEditPostBinding binding;
    String postID;
    String userId;
    String postIdNew;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;
    String avatar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        userId = Model.instance().getAuth().getCurrentUser().getUid();
        postID = getArguments().getString("postID");
        postIdNew = postID.substring(0, 28);
        Log.d("TAG", "onCreateView: " + postID.substring(0, 28));
        Log.d("TAG", "onCreateView: " + userId);
          if(!postIdNew.equals(userId)) {
            Toast.makeText(getContext(), "You can't edit this post", Toast.LENGTH_SHORT).show();

                //return to blue fragment
                Navigation.findNavController(container).navigateUp();
                return null;
        }


      else  {

            binding = FragmentEditPostBinding.inflate(inflater, container, false);
            View view = binding.getRoot();

            Model.instance().getPostById(postID, post -> {
                avatar = post.getAvatarUrl();
                binding.nameEt.setText(post.getTeacherName());
                binding.descEt.setText(post.getDescription());
                binding.price.setText(post.getPrice());
                if (!post.getAvatarUrl().equals("")) {
                    Picasso.get().load(post.getAvatarUrl()).into(binding.avatarImg);
                } else {
                    binding.avatarImg.setImageResource(R.drawable.avatar);
                }

            });

              binding.cancellBtn.setOnClickListener(view1 ->
                      Navigation.findNavController(view1).popBackStack(R.id.postListFragment,false));

            binding.saveBtn.setOnClickListener(view1 -> {
                String name = binding.nameEt.getText().toString();
                String desc = binding.descEt.getText().toString();
                String price = binding.price.getText().toString();
//                Model.instance().deletePostFromF(postID, (unused) -> {
//                   Model.instance().refreshAllPosts();
//                    });

                Post post = new Post(name, desc, price, postID, false, avatar, userId);
                if (isAvatarSelected) {
                    binding.avatarImg.setDrawingCacheEnabled(true);
                    binding.avatarImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
                    Model.instance().uploadImage(postID, bitmap, url -> {
                        if (url != null) {
                            post.setAvatarUrl(url);
                        }
                        Model.instance().addPost(post, (unused) -> {
//                            Navigation.findNavController(view).navigateUp();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            getActivity().finish();


                        });
                    });
                } else {
                    Model.instance().addPost(post, (unused) -> {
                        //back to profile and refresh
//                        Navigation.findNavController(view).navigateUp();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().finish();

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


    }