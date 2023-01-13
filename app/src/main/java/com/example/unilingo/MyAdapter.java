package com.example.unilingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class MyAdapter extends FirebaseRecyclerAdapter<Word, MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Word> list;

    public MyAdapter(FirebaseRecyclerOptions<Word> options) {
       super(options);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_word, parent, false);
        return new MyAdapter.MyViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Word model) {
        holder.word.setText(model.getWord());
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView word;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word);
        }
    }
}
