package com.iot.jeupromob.util;

public class Player {
    public String name;
    public int totalScore;
    public static volatile Player globalPlayer = new Player("joueur1");


    private Player(String name){
        this.name = name;

    }

    public static Player getInstance() {
        return globalPlayer;
    }

    public String getName(){
        return(this.name);
    }

    public void setName(String newName){
        globalPlayer.name= newName;
    }

    public int getTotalScore(){
        return(globalPlayer.totalScore);
    }

    public void addScore(int score){
        globalPlayer.totalScore+=score;
    }

    public void resetScore(){
        globalPlayer.totalScore=0;
    }


}