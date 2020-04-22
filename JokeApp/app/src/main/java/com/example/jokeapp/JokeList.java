package com.example.jokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JokeList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private JokeDao jokeDao;
    private List<Joke> jokeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("jokeAPP", "JokeList onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_list);
        jokeDao = JokeDatabase.getInstance(this).jokeDao();
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                jokeData = jokeDao.getJokes();
                Log.i("jokeAPP", jokeData.toString());

            }
        });
        dbThread.start();
        try {
            //We wait for the database to be done loading before we create the RecyclerView etc.
            dbThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new JokeAdapter(jokeData);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("jokeAPP", "JokeList onDestroy");
    }
}
