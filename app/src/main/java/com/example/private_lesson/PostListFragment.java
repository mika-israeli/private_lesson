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
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;
import java.util.List;

public class PostListFragment extends Fragment {
    List<Post> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        data = Model.instance().getAllPosts();
        RecyclerView list = view.findViewById(R.id.post_list_fragment);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(),data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            ///כאשר אני לוחצת על פוסט נפתח משהו
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = data.get(pos);
             PostListFragmentDirections.ActionPostListFragmentToBlueFragment action
                        = PostListFragmentDirections.actionPostListFragmentToBlueFragment(post.teacherName);
                Navigation.findNavController(view).navigate
                        (action);
            }
        });
       View addButton = view.findViewById(R.id.studentlistfrag_add_btn);
        NavDirections action = PostListFragmentDirections.actionGlobalAddPostFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));
        return view;
    }
}