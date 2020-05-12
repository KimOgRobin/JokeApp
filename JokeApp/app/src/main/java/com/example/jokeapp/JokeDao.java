package com.example.jokeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface JokeDao {

    @Query("SELECT * FROM Joke")
    List<Joke> getJokes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Joke joke);

    @Delete
    void delete (Joke joke);

    @Update
    int update(Joke joke);
}
