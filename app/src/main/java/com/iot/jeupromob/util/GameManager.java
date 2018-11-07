package com.iot.jeupromob.util;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.iot.jeupromob.R;
import com.iot.jeupromob.activity.BeerGameFragment;
import com.iot.jeupromob.activity.EndFragment;
import com.iot.jeupromob.activity.PetitBacFragment;
import com.iot.jeupromob.activity.QuizzGameFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    public int nbPlayers = 1;

    private static final int NB_GAMES_TO_PLAY = 3;

    //ordre d'exécution des défis généré aléatoirement dans la variable gamesOrder

    private int currentGameIndex = 0;
//    private Class[] gamesClass = {
//            PetitBac.class,
//            MouvementGame.class,
//            QuestionGame.class};
    //Stock les mini-jeux dans un ordre arbitraire
    private Fragment[] gamesFragment = null;
    //Stock les mini-jeux dans un ordre aléatoire
    private ArrayList<Fragment> gamesOrder = null;

    //Singleton ==> Une seule instance de cette classe, on ne peut pas en créer de nouveaux (constructeur privé)

    private static GameManager instance = new GameManager();
    private GameManager(){ }
    public static GameManager getInstance(){
        return instance;
    }

    //Lance le 1er mini jeu (pour commencer depuis mainActivity) et initialisation des variables

    public void start(AppCompatActivity mainActivity){
        //Intent intent = new Intent(mainActivity.getApplicationContext(), gamesOrder.get(currentGameIndex));
        //mainActivity.startActivity(intent);

        //On créer l'Array avec des nouveaux Fragments, on la mélange et on lance le 1er mini-jeu
        gamesFragment = new Fragment[]{
                new PetitBacFragment(),
                new QuizzGameFragment(),
                new BeerGameFragment()
        };
        gamesOrder = Random.shuffleArrayList(new ArrayList<Fragment>(Arrays.asList(gamesFragment)));
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, gamesOrder.get(0)).commit();
    }

    //Lance le prochain jeu

    public void nextGame(AppCompatActivity mainActivity){
        if(currentGameIndex != NB_GAMES_TO_PLAY - 1){
            currentGameIndex++;
            //Intent intent = new Intent(activity.getApplicationContext(), gamesOrder.get(currentGameIndex));
            //activity.startActivity(intent);
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, gamesOrder.get(currentGameIndex)).commit();
        }else{
            //Intent intent = new Intent(activity.getApplicationContext(), EndActivity.class);
            //activity.startActivity(intent);
            endGame(mainActivity);
        }
    }

    public void endGame(AppCompatActivity mainActivity){
        gamesFragment = null;
        gamesOrder.clear();
        currentGameIndex = 0;
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new EndFragment()).commit();
    }
}
