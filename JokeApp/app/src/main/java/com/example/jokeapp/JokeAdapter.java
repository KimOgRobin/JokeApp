package com.example.jokeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {
    private List<Joke> savedJokes;

    public JokeAdapter(List<Joke> savedJokes) {
        this.savedJokes = savedJokes;
    }

    public static class JokeViewHolder extends RecyclerView.ViewHolder{
        public TextView jokeTextView;
        public JokeViewHolder(View view, TextView textView) {
            super(view);
            jokeTextView = textView;
        }
    }

    @Override
    public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_view, parent, false);
        TextView textView = view.findViewById(R.id.jokeTextField);
        JokeViewHolder jokeViewHolder = new JokeViewHolder(view, textView);
        return jokeViewHolder;
    }

    @Override
    public void onBindViewHolder(JokeViewHolder holder, int position) {
        holder.jokeTextView.setText(savedJokes.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return savedJokes.size();
    }





}
