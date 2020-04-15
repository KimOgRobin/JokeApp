package com.example.jokeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Retrofit retrofit;
    private Thread thread;
    private TextView jokeText;
    private JokeService jokeAPI;
    private AppDatabase jokeDatabase;
    private JokeDao jokeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokeText = findViewById(R.id.jokeText);
        ratingBar = findViewById(R.id.ratingBar);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jokeAPI = retrofit.create(JokeService.class);
        jokeDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "jokeDatabase").build();
        jokeDao = jokeDatabase.jokeDao();
    }


    public void newJoke(View view){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Joke> jokeCall = jokeAPI.getJoke();
                try {
                    Joke theJoke = jokeCall.execute().body();
                    String jokeOnScreen = theJoke.toString();
                    jokeText.post(new Runnable() {
                        @Override
                        public void run() {
                            jokeText.setText(jokeOnScreen);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }

    public void saveJoke(View view){
        String[] jokeParts = jokeText.toString().split("\n");
        String setup = jokeParts[0];
        String punchline = jokeParts[1];
        float rating = ratingBar.getRating();
        Joke joke = new Joke(setup,punchline,rating);
        jokeDao.insert(joke);
    }

    public void showJokes(View view){

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
}
