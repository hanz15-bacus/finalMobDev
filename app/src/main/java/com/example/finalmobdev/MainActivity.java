package com.example.finalmobdev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button logoutbtn;
    Button editProfileBtn; // Added button to access EditProfileActivity
    TextView userDetails;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.main_logoutbtn);
        editProfileBtn = findViewById(R.id.main_edit_profile_btn); // Initialize the edit profile button
        userDetails = findViewById(R.id.main_txt);
        user = auth.getCurrentUser();

        if (user == null){
            Intent noUserIntent = new Intent(getApplicationContext(), login.class);
            startActivity(noUserIntent);
            finish();
        } else {
            userDetails.setText(user.getEmail());
        }

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                // Redirect to login page
                Intent loginIntent = new Intent(MainActivity.this, login.class);
                startActivity(loginIntent);
                finish();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EditProfileActivity
                Intent editProfileIntent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(editProfileIntent);
            }
        });
    }
}
