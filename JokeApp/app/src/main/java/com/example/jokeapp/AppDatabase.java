package com.example.jokeapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JokeDao jokeDao();
}

