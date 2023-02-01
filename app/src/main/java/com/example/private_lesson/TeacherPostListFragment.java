package com.example.private_lesson;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.private_lesson.databinding.FragmentPostListBinding;
import com.example.private_lesson.databinding.FragmentTeacherPostListBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import com.example.private_lesson.model.Teacher;


public class TeacherPostListFragment extends Fragment {

    FragmentTeacherPostListBinding binding;

    TeacherPostRecyclerAdapter adapter;
    TeacherPostsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeacherPostListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getMyData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            ///כאשר אני לוחצת על פוסט נפתח משהו
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = viewModel.getMyData().getValue().get(pos);
//                TeacherPostListFragmentDirections.ActionTeacherPostListFragmentToBlueFragment action
//                        = PostListFragmentDirections.actionPostListFragmentToBlueFragment(post.teacherName + " " + post.description + " " + post.price + " " + post.id);
//                Navigation.findNavController(view).navigate
//                        (action);
//            }
            }
        });
//        View addButton = view.findViewById(R.id.btnAdd);
//
//        NavDirections action = PostListFragmentDirections.actionGlobalAddPostFragment();
//        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
//        binding.progressBar.setVisibility(View.GONE);
//        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
//            adapter.setData(list);

//        });
        Model.instance().EventTeachersListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(TeacherPostsListFragmentViewModel.class);
    }


    void reloadData() {
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllPosts();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_post_list, container, false);
    }

}