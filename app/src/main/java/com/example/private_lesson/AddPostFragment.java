package com.example.private_lesson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;

public class AddPostFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       FragmentActivity parentActivity= getActivity();
       parentActivity.addMenuProvider(new MenuProvider() {


           @Override
           public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
               menu.removeItem(R.id.addPostFragment);


           }

           @Override
           public boolean onMenuItemSelected(MenuItem item) {

               return false;
           }
       },this, Lifecycle.State.RESUMED);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);


        EditText nameEt = view.findViewById(R.id.nameEt);
        EditText idEt = view.findViewById(R.id.idEt);
        EditText descriptionEt = view.findViewById(R.id.descEt);
        EditText priceEt = view.findViewById(R.id.price);;
        Button saveBtn = view.findViewById(R.id.saveBtn);
        Button cancelBtn = view.findViewById(R.id.cancellBtn);


                    saveBtn.setOnClickListener(view1 -> {
                String name = nameEt.getText().toString();
                String id = idEt.getText().toString();
                String description = descriptionEt.getText().toString();
                String price = priceEt.getText().toString();
                Post post = new Post(name, description,id ,price,false,"");
                Model.instance().addPost(post, () -> {

                    Navigation.findNavController(view1).popBackStack();
                });

            });

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1)
                .popBackStack(R.id.postListFragment,false));
        Log.d("TAG", "fail  : ");
        setHasOptionsMenu(true);
        return view;
    }



}