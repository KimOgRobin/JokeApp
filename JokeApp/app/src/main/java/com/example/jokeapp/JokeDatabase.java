package com.example.jokeapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1)
public abstract class JokeDatabase extends RoomDatabase {
    public abstract JokeDao jokeDao();
    private static JokeDatabase INSTANCE;

    public static JokeDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    JokeDatabase.class, "jokeDatabase").build();
        }
        return INSTANCE;
    }


}

