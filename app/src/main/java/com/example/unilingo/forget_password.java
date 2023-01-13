package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.C;

public class forget_password extends AppCompatActivity {

    public EditText emailId;
    public Button resetBtn;
    public TextView loginLink;
    public ProgressBar progressBar1;
    public RelativeLayout progressBar2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailId = findViewById(R.id.resetEmail);
        resetBtn = findViewById(R.id.resetBtn);
        loginLink = findViewById(R.id.linkToLogin);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar1.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.VISIBLE);
                passwordReset();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(forget_password.this, login_screen.class);
                startActivity(i);
            }
        });


    }

    protected void passwordReset(){
        String email = emailId.getText().toString();

        if (TextUtils.isEmpty(email)) {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                            "Please enter email",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar1.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),
                                            "Link sent",
                                            Toast.LENGTH_LONG)
                                    .show();
                            Intent i = new Intent(forget_password.this, forget_password_success.class);
                            startActivity(i);
                        }
                        else{
                            progressBar1.setVisibility(View.GONE);
                            progressBar2.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),
                                            "Failed to send link",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

}