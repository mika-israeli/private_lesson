package com.example.private_lesson;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.private_lesson.databinding.FragmentApiBinding;
import com.example.private_lesson.databinding.FragmentPostListBinding;
import com.example.private_lesson.model.Education;
import com.example.private_lesson.model.EducationModel;
import com.example.private_lesson.model.Model;
import com.example.private_lesson.model.Post;

import java.util.List;

public class ApiFragment extends Fragment {

    FragmentApiBinding binding;
    ApiRecyclerAdapter adapter;

    ApiViewModelFragment viewModel;


    @Nullable
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentApiBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ApiRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getData().observe(getViewLifecycleOwner(), data -> {
            adapter.setData(data);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ApiViewModelFragment.class);
    }
}