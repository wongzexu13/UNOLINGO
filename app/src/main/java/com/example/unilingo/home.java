package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Dictionary;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Button quiz;
    public TextView profile;
    public TextView history;
    public TextView setting;
    public static TextView word;
    public static TextView meaning;
    public static TextView percentage;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;
    public RelativeLayout learn;
    public static int id = 0, idTemp=0, pct = 0, pctTemp = 0, cycle=0, MAX=30, buffer=0;
    static String wordID, wordIDTemp;
    static DatabaseReference reference;
    private long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nextWord();

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.menu_view);
        toolbar = findViewById(R.id.toolbar);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        percentage = findViewById(R.id.percentage);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.menu_home);

        quiz = findViewById(R.id.attemptQuiz);
        profile = findViewById(R.id.profile);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pct==100){
                    cycle=0;
                    pct=0;
                    pctTemp=0;
                    id=idTemp;
                    Intent i = new Intent(home.this, quiz.class);
                    i.putExtra("wordID", wordIDTemp);
                    startActivity(i);
                }else if(id == MAX){
                    Toast.makeText(getBaseContext(), "You've reached the end", Toast.LENGTH_SHORT).show();
                    percentage.setText("100");
                }else{
                    Toast.makeText(getBaseContext(), "Quiz can be unlocked at 100%", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, profile.class);
                startActivity(i);
            }
        });

        learn = (RelativeLayout) findViewById(R.id.learn);
        learn.setOnTouchListener(new ActivitySwipeDetector(this));

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(pressedTime + 2000 > System.currentTimeMillis()){
            //super.onBackPressed();
            finish();
            moveTaskToBack(true);
        }else{
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_history:
                Intent j = new Intent(home.this, history.class);
                startActivity(j);
                break;

            case R.id.menu_scoreboard:
                Intent o = new Intent(home.this, scoreboard.class);
                startActivity(o);
                break;

            case R.id.menu_qrCode:
                Intent m = new Intent(home.this, add_friend.class);
                startActivity(m);
                break;

            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Intent k = new Intent(home.this, login_screen.class);
                startActivity(k);
                Toast.makeText(this, "Logout Success", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_setting:
                Intent l = new Intent(home.this, setting.class);
                startActivity(l);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected static void nextWord(){


        String wordTemp, meaningTemp;


        if(id==MAX){
            wordTemp = "Congrats";
            meaningTemp= "You've reached the end";
            word.setText(wordTemp);
            meaning.setText(meaningTemp);
        }else if(cycle==6){
            wordTemp = "Attempt quiz now";
            meaningTemp= "You've reached your daily goal";
            word.setText(wordTemp);
            meaning.setText(meaningTemp);
        }else{
            if(id<MAX) {

                id++;

                if(idTemp < id)
                    idTemp = id;

                wordIDTemp = String.valueOf(idTemp);
                wordID = String.valueOf(id);

                reference = FirebaseDatabase.getInstance().getReference("Dictionary");
                reference.child(wordID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                String wordTemp = String.valueOf(dataSnapshot.child("word").getValue());
                                String meaningTemp = String.valueOf(dataSnapshot.child("meaning").getValue());
                                word.setText(wordTemp);
                                meaning.setText(meaningTemp);

                                if (cycle<=5) {
                                    if(buffer!=0){
                                        cycle++;
                                        buffer--;
                                    }else if(cycle==0){
                                        pct = cycle*20;
                                        percentage.setText(String.valueOf(pct));
                                        cycle++;
                                    }else{
                                        pct = cycle*20;
                                        if(pctTemp >= pct){
                                            pct = pctTemp;
                                        }else{
                                            pctTemp = pct;
                                        }
                                        percentage.setText(String.valueOf(pct));
                                        cycle++;
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    protected static void previousWord(){

        if(id>1) {
            id--;
            buffer++;
            cycle--;
            wordID = String.valueOf(id);

            reference = FirebaseDatabase.getInstance().getReference("Dictionary");
            reference.child(wordID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String wordTemp = String.valueOf(dataSnapshot.child("word").getValue());
                            String meaningTemp = String.valueOf(dataSnapshot.child("meaning").getValue());
                            word.setText(wordTemp);
                            meaning.setText(meaningTemp);
                        }
                    }
                }
            });
        }
    }
}