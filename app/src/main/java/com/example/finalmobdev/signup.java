package com.example.finalmobdev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    EditText fullname;
    EditText email;
    EditText subdivision;
    EditText block;
    EditText lot;
    EditText password;
    Button signupbtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView logintxt;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent login = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(login);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        subdivision = findViewById(R.id.signup_subdivision);
        block = findViewById(R.id.signup_block);
        lot = findViewById(R.id.signup_lot);
        password = findViewById(R.id.signup_password);
        signupbtn = findViewById(R.id.signup_signupbtn);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        logintxt = findViewById(R.id.signup_loginbtn);

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openlogin = new Intent(getApplicationContext(), login.class);
                startActivity(openlogin);
                finish();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String sfullname, semail, ssubdivision, sblock, slot, spassword;
                sfullname = String.valueOf(fullname.getText());
                semail = String.valueOf(email.getText());
                ssubdivision = String.valueOf(subdivision.getText());
                sblock = String.valueOf(block.getText());
                slot = String.valueOf(lot.getText());
                spassword = String.valueOf(password.getText());

                if(TextUtils.isEmpty(sfullname)){
                    Toast.makeText(signup.this, "Enter Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(semail)){
                    Toast.makeText(signup.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(ssubdivision)){
                    Toast.makeText(signup.this, "Enter Subdivision", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(sblock)){
                    Toast.makeText(signup.this, "Enter Block", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(slot)){
                    Toast.makeText(signup.this, "Enter Lot", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(spassword)){
                    Toast.makeText(signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(semail, spassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(signup.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}