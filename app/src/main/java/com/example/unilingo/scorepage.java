package com.example.unilingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class scorepage extends AppCompatActivity {

    Button confirm;
    public static TextView congrats, correct, wrong, marks;
    String congratsS, correctS, wrongS, marksS;
    int correctTemp, wrongTemp;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorepage);

        Intent i = getIntent();
        correctS = i.getStringExtra("correct");
        wrongS = i.getStringExtra("wrong");

        congrats = findViewById(R.id.congrats);
        correct = findViewById(R.id.correct);
        wrong = findViewById(R.id.wrong);
        marks = findViewById(R.id.marks);

        correctTemp = Integer.parseInt(correctS);
        wrongTemp = Integer.parseInt(wrongS);

        float marksTemp = (float) correctTemp / 6 * 100;
        marksS = String.valueOf(df.format(marksTemp));

        if(correctTemp==6){
            congrats.setText("Excellent");
        }else if(correctTemp>3&&correctTemp<6){
            congrats.setText("Well done");
        }else{
            congrats.setText("Keep it up");
        }

        marks.setText(marksS+"%");
        correct.setText(correctS);
        wrong.setText(wrongS);


        confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(scorepage.this, home.class);
                startActivity(i);
            }
        });

    }


}