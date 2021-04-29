package com.bits.frenchify;

import java.util.Date;

public class ScoreSender {
    String date,category;


    int score;

    public ScoreSender(String date, String category, int score) {
        this.date = date;
        this.category = category;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }






}
