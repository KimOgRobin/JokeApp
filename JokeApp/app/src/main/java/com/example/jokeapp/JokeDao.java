package com.example.jokeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface JokeDao {

    @Query("SELECT * FROM Joke")
    Joke getJoke();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Joke joke);
    @Query("DELETE FROM Joke WHERE ID = :ID ")
    void delete(int ID);
}
