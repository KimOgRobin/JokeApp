package com.example.jokeapp;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
interface JokeService {

    @GET("random_joke")
    public Call<Joke> getJoke();

    @GET("random_ten")
    public Call<List<Joke>> getTenJokes();
}
