package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_screen extends AppCompatActivity {

    public Button signUp;
    public TextView login;
    private FirebaseAuth mAuth;
    DatabaseReference reference, pushRef;
    private EditText email_id;
    private EditText password_id1, password_id2;
    public ProgressBar progressBar1;
    public RelativeLayout progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth = FirebaseAuth.getInstance();

        signUp = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.linkToLogin);
        email_id = findViewById(R.id.registerEmail);
        password_id1 = findViewById(R.id.registerPassword1);
        password_id2 = findViewById(R.id.registerPassword2);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                registerNewUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(register_screen.this, login_screen.class);
                startActivity(i);
            }
        });
    }

    private void registerNewUser(){

        String email = email_id.getText().toString();
        String password = password_id2.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                            "Please enter email",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                            "Please enter password",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(register_screen.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful",
                                            Toast.LENGTH_LONG)
                                    .show();
                            user.sendEmailVerification();

//                            String childID = "user";
//                            String childValue = "userId";
//
//                            reference = FirebaseDatabase.getInstance().getReference().child(childID).child(childValue);
//                            pushRef = reference.push();
//                            pushRef.setValue(user);


                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(register_screen.this,
                                    register_success.class);
                            startActivity(intent);
                        } else {
                            progressBar1.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Failed to register",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }



}