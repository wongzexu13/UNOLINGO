package com.example.unilingo;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class history extends AppCompatActivity {

    private RecyclerView recyclerView;
    public Button homeBack;

    DatabaseReference reference;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        homeBack = findViewById(R.id.home);

        homeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history.super.onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycleview);
        reference = FirebaseDatabase.getInstance().getReference("Dictionary");
        Query query = reference.limitToFirst(home.idTemp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Word> options = new FirebaseRecyclerOptions.Builder<Word>().setQuery(query, Word.class).build();

        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}