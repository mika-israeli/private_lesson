package com.example.private_lesson;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.private_lesson.databinding.FragmentSignInBinding;
import com.example.private_lesson.model.Model;

public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
    String userId;


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
            }, this, Lifecycle.State.RESUMED);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding= FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.signInb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailSignIn.getText().toString();
                String password = binding.passwordSignIn.getText().toString();

                if(password.length() < 6) {
                    binding.passwordSignIn.setError("Password must be at least 6 characters");
                    return;
                }
                if(email.isEmpty()) {
                    binding.emailSignIn.setError("Email is required");
                    return;
                }
                Model.instance().signIn(email, password, task -> {
                    if(!task) {
                       Toast.makeText(MyApplication.getMyContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MyApplication.getMyContext(), "Authentication succeeded", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyApplication.getMyContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }

                });

            }
            
        });

        return view;
    }
}