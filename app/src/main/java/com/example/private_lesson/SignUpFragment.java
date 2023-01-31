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
import android.widget.Button;
import android.widget.EditText;

import com.example.private_lesson.databinding.FragmentSignUpBinding;
import com.example.private_lesson.model.Model;


public class SignUpFragment extends Fragment {

    FragmentSignUpBinding binding;
    String userId;

    public static SignUpFragment newInstance(String userId) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }


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
                return false;}
            },this,Lifecycle.State.RESUMED);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        binding = FragmentSignUpBinding.bind(view);
        binding.signUpb.setOnClickListener(v -> {
            String email = binding.emailSignUp.getText().toString();
            String password = binding.passwordSignUp.getText().toString();
            if(password.length() < 6){
                binding.passwordSignUp.setError("Password must be at least 6 characters");
                return;
            }
            Model.instance().signUp(email, password, data -> {

                if(data.equals("")){
                    return;
                }
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_addTeacherFragment);
            });
        });


        return view;
    }

}