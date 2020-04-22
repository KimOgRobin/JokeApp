package com.example.jokeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String JOKE_DATA = "com.example.jokeapp.JOKEDATA";
    private RatingBar ratingBar;
    private Retrofit retrofit;
    private Thread thread;
    private TextView jokeText;
    private JokeService jokeAPI;
    private JokeDao jokeDao;
    private boolean threadRunning;
    private LinkedList<String> jokes;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokeText = findViewById(R.id.jokeText);
        ratingBar = findViewById(R.id.ratingBar);
        threadRunning = true;
        jokes = new LinkedList<>();
        jokeDao = JokeDatabase.getInstance(this).jokeDao();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jokeAPI = retrofit.create(JokeService.class);


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRunning) {
                    Call<List<Joke>> jokeCall = jokeAPI.getTenJokes();
                    jokeCall.enqueue(new Callback<List<Joke>>() {
                        @Override
                        public void onResponse(Call<List<Joke>> call, Response<List<Joke>> response) {
                            List<Joke> jokeList = response.body();
                            if (jokeList != null) {
                                for (Joke joke : jokeList) {
                                    jokes.add(joke.toString());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Joke>> call, Throwable t) {
                            Log.i("jokeAPP", "joke error");
                        }
                    });

                    Log.i("jokeAPP", "thread cycle " + counter++);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void newJoke(View view) {
        if (!jokes.isEmpty()) {
            jokeText.setText(jokes.getFirst());
            jokes.removeFirst();
        } else {
            String message = getString(R.string.loading_jokes) + "\n" + getString(R.string.joke_limit);
            Log.i("jokeAPP", message);
            jokeText.setText(message);
        }
    }

    public void saveJoke(View view){
        if(jokeText.getText() != "") {
            String[] jokeParts = jokeText.getText().toString().split("\n");
            String setup = jokeParts[0];
            String punchline = jokeParts[1];

            float rating = ratingBar.getRating();
            Joke joke = new Joke();
            joke.setup = setup;
            joke.punchline = punchline;
            joke.rating = rating;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jokeDao.insert(joke);
                }
            }).start();
        }
    }

    public void showJokes(View view) {
        Intent intent = new Intent(this, JokeList.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //The text in the jokeText view is saved so it can be used after configuration changes, because android does not save the state of textView
        outState.putString("joke", String.valueOf(jokeText.getText()));
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //Here the joke is loaded and put into the jokeText View so it can be seen after configuration changes
        jokeText.setText((CharSequence) savedInstanceState.get("joke"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        threadRunning = false;
        Log.i("jokeAPP", "onDestroy");
        super.onDestroy();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
