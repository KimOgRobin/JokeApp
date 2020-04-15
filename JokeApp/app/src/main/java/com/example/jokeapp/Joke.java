package com.example.jokeapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class Joke {
    @PrimaryKey(autoGenerate = true)
    public int ID;
    @ColumnInfo(name = "setup")
    public String setup;
    @ColumnInfo(name = "punchline")
    public String punchline;
    @ColumnInfo(name = "rating")
    public float rating;


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
