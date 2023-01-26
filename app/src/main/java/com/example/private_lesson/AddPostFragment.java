package com.example.private_lesson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPostFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);


        EditText nameEt = view.findViewById(R.id.addpost_name_et);
        EditText idEt = view.findViewById(R.id.addpost_id_et);
        TextView messageTv = view.findViewById(R.id.addspost_message);
        Button saveBtn = view.findViewById(R.id.addpost_save_btn);
        Button cancelBtn = view.findViewById(R.id.addpost_cancell_btn);

        saveBtn.setOnClickListener(view1 -> {
            String name = nameEt.getText().toString();
            messageTv.setText(name);
        });

        cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1)
                .popBackStack(R.id.postsListFragment,false));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        //super.onCreateOptionsMenu(menu, inflater);

    }

}