package com.example.private_lesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.private_lesson.R;
import com.example.private_lesson.model.Model;
import com.google.firebase.auth.FirebaseUser;

public class LogInOrSignUpActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_or_sign_up);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().
                findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();



        FirebaseUser user = Model.instance().getAuth().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}