package com.example.private_lesson;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
    List<Post> data=new LinkedList<>();
    PostRecyclerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(),data);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            ///כאשר אני לוחצת על פוסט נפתח משהו
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = data.get(pos);
             PostListFragmentDirections.ActionPostListFragmentToBlueFragment action
                        = PostListFragmentDirections.actionPostListFragmentToBlueFragment(post.teacherName + " " + post.description + " " + post.price + " " + post.id);
                Navigation.findNavController(view).navigate
                        (action);
            }
        });
       View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = PostListFragmentDirections.actionGlobalAddPostFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData(){
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllPosts((poList)->{
            data = poList;
            adapter.setData(data);
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}