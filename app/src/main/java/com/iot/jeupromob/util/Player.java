package com.iot.jeupromob.util;

public class Player {
    public String name;
    public int totalScore;


    public Player(String name){
        this.name = name;

    }

    public String getName(){
        return(this.name);
    }

    public int getTotalScore(){
        return(this.totalScore);
    }

    public void addScore(int score){
        this.totalScore+=score;
    }

}
