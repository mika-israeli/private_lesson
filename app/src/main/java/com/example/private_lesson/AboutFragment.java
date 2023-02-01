package com.example.private_lesson;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.private_lesson.databinding.FragmentAboutBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class AboutFragment extends Fragment {

    FragmentAboutBinding binding;
    PostRecyclerAdapter adapter;
    private PostsListFragmentViewModel viewModel;
    String userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        activity.addMenuProvider(new MenuProvider() {


            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.aboutFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        reload();
        //get the user id from auth
        userId = Model.instance().getAuth().getCurrentUser().getUid();
        Model.instance().getTeacherById(userId, teacher -> {
            binding.aboutName.setText("Teacher Name: " + teacher.getTeacherName());
            if (!teacher.getAvatarUrl().equals("")) {
                Picasso.get().load(teacher.getAvatarUrl()).placeholder(R.drawable.avatar).into(binding.aboutImg);
            } else {
                binding.aboutImg.setImageResource(R.drawable.avatar);
            }

        });
        binding.signOutBtn.setOnClickListener(v -> {
            Model.instance().getAuth().signOut();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        });

        binding.editBtn.setOnClickListener(v -> {

            //go to editabout fragment with the user id
            NavDirections action = AboutFragmentDirections.actionAboutFragmentToEditAboutFragment(userId);
            Navigation.findNavController(v).navigate(action);


        });

        binding.seeAllPostsBtn.setOnClickListener(v -> {
            //go to posts list fragment with the user id
            NavDirections action = AboutFragmentDirections.actionAboutFragmentToTeacherPostListFragment("1", userId);
            Navigation.findNavController(v).navigate(action);
        });

        binding.API.setOnClickListener(v -> {
            //go to posts list fragment with the user id
            NavDirections action = AboutFragmentDirections.actionAboutFragmentToApiFragment();
            Navigation.findNavController(v).navigate(action);
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }


    public void reload(){
        userId = Model.instance().getAuth().getCurrentUser().getUid();
        Model.instance().getTeacherById(userId, teacher -> {
            binding.aboutName.setText("Teacher Name: " + teacher.getTeacherName());
            if (!teacher.getAvatarUrl().equals("")) {
                Picasso.get().load(teacher.getAvatarUrl()).placeholder(R.drawable.avatar).into(binding.aboutImg);
            } else {
                binding.aboutImg.setImageResource(R.drawable.avatar);
            }
        });
    }


}


