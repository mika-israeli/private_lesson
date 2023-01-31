package com.example.private_lesson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.private_lesson.databinding.FragmentLogInOrSignUpBinding;


public class LogInOrSignUpFragment extends Fragment {

    FragmentLogInOrSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        activity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
            },this,Lifecycle.State.RESUMED);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogInOrSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.loginBtn.setOnClickListener(v ->  {
            NavDirections action = LogInOrSignUpFragmentDirections.
                    actionLogInOrSignUpFragmentToSignUpFragment();
            Navigation.findNavController(view).navigate(action);
        });

        binding.sighupBtn.setOnClickListener(v -> {
            NavDirections action = LogInOrSignUpFragmentDirections.
                    actionLogInOrSignUpFragmentToSignUpFragment();
            Navigation.findNavController(view).navigate(action);
        });
        return view;


    }
}