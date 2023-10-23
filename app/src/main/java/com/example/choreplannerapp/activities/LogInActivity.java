package com.example.choreplannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.choreplannerapp.R;
import com.example.choreplannerapp.fragments.LogInFragment;
import com.example.choreplannerapp.interfaces.LogInInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements LogInInterface {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.log_in_fragment_container, LogInFragment.newInstance())
                .addToBackStack("login")
                .commit();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null) {
            Log.v("GOOGLETAG", "Account token: LOG IN SCREEN ON START OVERRIDE: " + currentUser.getUid());
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }
        //use current user here to open the home fragment with user as a parameter to update all ui to their data
    }


    @Override
    public void goToMain() {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
