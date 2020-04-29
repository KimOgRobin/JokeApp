package com.example.jokeapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {
    private List<Joke> savedJokes;
    private Context contextForDb;

    public JokeAdapter(List<Joke> savedJokes, Context context) {
        this.savedJokes = savedJokes;
        this.contextForDb = context;
    }

    public static class JokeViewHolder extends RecyclerView.ViewHolder{
        public TextView jokeTextView;
        public RatingBar ratingBar;
        public JokeViewHolder(View view, TextView textView, RatingBar ratingbar) {
            super(view);
            jokeTextView = textView;
            this.ratingBar = ratingbar;
        }
    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_view, parent, false);
        TextView textView = view.findViewById(R.id.jokeTextField);
        RatingBar ratingbar = view.findViewById(R.id.jokeRatingBar);
        JokeViewHolder jokeViewHolder = new JokeViewHolder(view, textView, ratingbar);
        return jokeViewHolder;
    }

    @Override
    public void onBindViewHolder(JokeViewHolder holder, int position) {
        holder.jokeTextView.setText(savedJokes.get(position).toString());
        holder.ratingBar.setRating(savedJokes.get(position).rating);
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                final int tempPos = position;
                savedJokes.get(tempPos).rating = rating;
                Thread dbThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JokeDatabase.getInstance(contextForDb).jokeDao().update(savedJokes.get(tempPos));
                    }
                });
                dbThread.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedJokes.size();
    }
}
