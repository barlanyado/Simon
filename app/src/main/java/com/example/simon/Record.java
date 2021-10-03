package com.example.simon;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Record implements Serializable {

    private final String name;
    private int score;
    private int level;
    private int rate;


    public Record(String name) {
        this.name = name;
        this.score = 0;
        this.level = 1;
        this.rate = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return   rate  + "           " + name +
                "           " + level +
                "           " + score;
    }
}
