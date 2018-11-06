package com.iot.jeupromob;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class GameManager {
    //ordre d'exécution des défis

    private int currentGame = 0;
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
        Intent intent = new Intent(mainActivity.getApplicationContext(), gameOrder.get(currentGame));
        mainActivity.startActivity(intent);
    }

    //Lance le prochain jeu depuis l'activité courante
    public void nextGame(AppCompatActivity activity){
        currentGame++;
        Intent intent = new Intent(activity.getApplicationContext(), gameOrder.get(currentGame));
        activity.startActivity(intent);
    }
}
