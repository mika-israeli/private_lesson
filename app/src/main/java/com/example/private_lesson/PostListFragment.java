package com.example.private_lesson;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.private_lesson.databinding.FragmentPostListBinding;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostListFragment extends Fragment {
    FragmentPostListBinding binding;

    PostRecyclerAdapter adapter;
   PostsListFragmentViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            ///כאשר אני לוחצת על פוסט נפתח משהו
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = viewModel.getData().getValue().get(pos);
                String postID = post.id;
                String userId = post.userId;
             PostListFragmentDirections.ActionPostListFragmentToBlueFragment action
                        = PostListFragmentDirections.actionPostListFragmentToBlueFragment(userId,postID);
                Navigation.findNavController(view).navigate
                        (action);
            }
        });
       View addButton = view.findViewById(R.id.btnAdd);



        NavDirections action = PostListFragmentDirections.actionGlobalAddPostFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        binding.progressBar.setVisibility(View.GONE);
        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);

        });
        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), status -> {
           binding.swipeRefresh.setRefreshing(status== Model.LoadingState.LOADING);
        });
        binding.swipeRefresh.setOnRefreshListener(()->{
                reloadData();
    });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
    }


    void reloadData(){
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().refreshAllPosts();

    }
}