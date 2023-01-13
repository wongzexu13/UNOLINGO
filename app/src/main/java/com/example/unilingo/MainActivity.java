package com.example.unilingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;


    View firstbar;
    Animation anim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        //To make this activity as Splash Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Link anim file with java
        anim1= AnimationUtils.loadAnimation(this, R.anim.animation);

        //Hook the animation widget
        firstbar = findViewById(R.id.splashLogo);

        //Start the animation
        firstbar.setAnimation(anim1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //We can create explicit intent
                Intent intent = new Intent(MainActivity.this, login_screen.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}