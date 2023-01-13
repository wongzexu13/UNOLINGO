package com.example.unilingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    public TextView attempt;
    public Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        home = findViewById(R.id.home);
        attempt = findViewById(R.id.attempt);

        attempt.setText(String.valueOf(quiz.attempt));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.super.onBackPressed();
            }
        });
    }
}