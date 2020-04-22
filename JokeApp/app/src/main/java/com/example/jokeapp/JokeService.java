package com.example.jokeapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface JokeService {

    @GET("random_joke")
    public Call<Joke> getJoke();

    @GET("random_ten")
    public Call<List<Joke>> getTenJokes();
}
