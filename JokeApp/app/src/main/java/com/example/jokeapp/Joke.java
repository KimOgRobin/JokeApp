package com.example.jokeapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class Joke {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "setup")
    private String setup;
    @ColumnInfo(name = "punchline")
    private String punchline;
    @ColumnInfo(name = "rating")
    private float rating;

    public Joke(String setup, String punchline, float rating){
        this.setup = setup;
        this.punchline = punchline;
        this.rating = rating;
    }
    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public float getRating() {
        return rating;
    }

    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    @Override
    public String toString() {
        return setup + "\n" + punchline;
    }

}
