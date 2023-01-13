package com.example.unilingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_friend extends AppCompatActivity {

    public Button scanQR, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        scanQR = findViewById(R.id.scanQR);
        home = findViewById(R.id.home);

        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(add_friend.this, com.example.unilingo.scanQR.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_friend.super.onBackPressed();
            }
        });


    }
}