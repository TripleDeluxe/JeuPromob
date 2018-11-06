package com.iot.jeupromob;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class GameManager {
    public int nbPlayers = 1;

    private static final int NB_GAMES_TO_PLAY = 3;

    //ordre d'exécution des défis
    private int currentGameIndex = 0;

    //ArrayList des Class des activités des jeux et
    private ArrayList<Class> gamesClass = new ArrayList();
    private ArrayList<Class> gameOrder;

    //Singleton ==> Une seule instance de cette classe, on ne peut pas en créer de nouveaux (constructeur privé)
    private static GameManager instance = new GameManager();

    private GameManager(){
        gamesClass.add(PetitBac.class);
        gamesClass.add(MouvementGame.class);
        gamesClass.add(QuestionGame.class);

        gameOrder = Random.shuffleArrayList(gamesClass);
    }

    public static GameManager getInstance(){
        return instance;
    }

    //Lance le 1er mini jeu (pour commencer depuis mainActivity)

    public void start(AppCompatActivity mainActivity){
        Intent intent = new Intent(mainActivity.getApplicationContext(), gameOrder.get(currentGameIndex));
        mainActivity.startActivity(intent);
    }

    //Lance le prochain jeu depuis l'activité courante (un des mini-jeux) ou l'activité de fin

    public void nextGame(AppCompatActivity activity){
        if(currentGameIndex != NB_GAMES_TO_PLAY - 1){
            currentGameIndex++;
            Intent intent = new Intent(activity.getApplicationContext(), gameOrder.get(currentGameIndex));
            activity.startActivity(intent);
        }else{
            Intent intent = new Intent(activity.getApplicationContext(), EndActivity.class);
            activity.startActivity(intent);
        }
    }
}
