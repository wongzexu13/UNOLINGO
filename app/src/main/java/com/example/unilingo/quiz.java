package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class quiz extends AppCompatActivity {

    private static int QUIZ_COUNTDOWN = 10000;

    View timer;
    Animation anim1;
    public static int id, MAX=6, n=0, attempt=0;
    DatabaseReference reference;
    public AppCompatButton option1, option2, option3, option4;
    public TextView meaning;
    public String wordTemp, meaningTemp, option1Temp, option2Temp, option3Temp, option4Temp, answer, idS;
    public String correct, wrong;
    public int correctTemp=0, wrongTemp=0;
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent i = getIntent();
        idS = i.getStringExtra("wordID");

        id = Integer.parseInt(idS);
        id = id-6;

        meaning = findViewById(R.id.meaning);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextQuiz();
        animationView();
        delay();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = option1.getText().toString();
                checkAnswer(answer);
                handler.removeCallbacksAndMessages(null);
                delay();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = option2.getText().toString();
                checkAnswer(answer);
                handler.removeCallbacksAndMessages(null);
                delay();
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = option3.getText().toString();
                checkAnswer(answer);
                handler.removeCallbacksAndMessages(null);
                delay();
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = option4.getText().toString();
                checkAnswer(answer);
                handler.removeCallbacksAndMessages(null);
                delay();
            }
        });
    }

    protected void nextQuiz(){
        if(n<MAX) {
            animationView();
            id++;
            n++;
            String wordID = String.valueOf(id);

            reference = FirebaseDatabase.getInstance().getReference("Dictionary");
            reference.child(wordID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            option1Temp = String.valueOf(dataSnapshot.child("option1").getValue());
                            option2Temp = String.valueOf(dataSnapshot.child("option2").getValue());
                            option3Temp = String.valueOf(dataSnapshot.child("option3").getValue());
                            option4Temp = String.valueOf(dataSnapshot.child("option4").getValue());
                            meaningTemp = String.valueOf(dataSnapshot.child("meaning").getValue());
                            wordTemp = String.valueOf(dataSnapshot.child("word").getValue());
                            meaning.setText(meaningTemp);
                            option1.setText(option1Temp);
                            option2.setText(option2Temp);
                            option3.setText(option3Temp);
                            option4.setText(option4Temp);
                        }
                    }
                }
            });
        }else{
            handler.removeCallbacksAndMessages(null);
            n=0;
            correct=String.valueOf(correctTemp);
            wrong=String.valueOf(wrongTemp);
            Intent i = new Intent(quiz.this, scorepage.class);
            i.putExtra("correct", correct);
            i.putExtra("wrong", wrong);
            startActivity(i);
            correctTemp=0;
            wrongTemp=0;
            attempt++;
        }
    }

    protected void animationView(){
        //Link anim file with java
        anim1= AnimationUtils.loadAnimation(this, R.anim.quiz_timer_countdown);

        //Hook the animation widget
        timer = findViewById(R.id.timerCountdown);

        //Start the animation
        timer.setAnimation(anim1);
    }

    protected void checkAnswer(String ans){

        if(ans.equals(wordTemp)){
            correctTemp++;
        }else{
            wrongTemp++;
        }
        nextQuiz();
    }

    protected void delay(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wrongTemp++;
                nextQuiz();
            }
        }, 10000);
    }
}