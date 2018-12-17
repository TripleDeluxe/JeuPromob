package com.iot.jeupromob.util;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.iot.jeupromob.R;
import com.iot.jeupromob.activity.BeerGameFragment;
import com.iot.jeupromob.activity.EndFragment;
import com.iot.jeupromob.activity.MainActivity;
import com.iot.jeupromob.activity.MultiplayerMenuFragment;
import com.iot.jeupromob.activity.ObstacleFragment;
import com.iot.jeupromob.activity.PetitBacFragment;
import com.iot.jeupromob.activity.QuizzGameFragment;
import com.iot.jeupromob.activity.ShapeGameFragment;
import com.iot.jeupromob.activity.TaupeFragment;
import com.iot.jeupromob.activity.TrainingFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    private Fragment[] gamesFragment = null;
    private static final int NB_GAMES_TO_PLAY = 3;
    private int currentGameIndex = 0;
    //Stock les mini-jeux dans un ordre aléatoire
    private ArrayList<Fragment> gamesOrder = null;
    private String mCurrentMode = "";

    public Player user = new Player("");

    //Singleton ==> Une seule instance de cette classe, on ne peut pas en créer de nouveaux (constructeur privé)
    private static GameManager instance = new GameManager();
    private GameManager(){ }
    public static GameManager getInstance(){
        return instance;
    }

    public Fragment[] getMiniGamesFragments(){
        return gamesFragment;
    }

    //Lance le 1er mini jeu (pour commencer depuis mainActivity) et initialisation des variables
    public void startSoloGame(MainActivity mainActivity){
        mCurrentMode = "solo";

        //On créer l'Array avec des nouveaux Fragments, on la mélange et on lance le 1er mini-jeu
        gamesFragment = new Fragment[]{
                //new PetitBacFragment(),
                new QuizzGameFragment(),
                new ObstacleFragment(),
                new TaupeFragment(),
                new ShapeGameFragment()
        };

        gamesOrder = Random.shuffleArrayList(new ArrayList<Fragment>(Arrays.asList(gamesFragment)));
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, gamesOrder.get(0)).commit();
    }

    public void startMultiGame(MainActivity mainActivity){
        mCurrentMode = "multi";
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new MultiplayerMenuFragment()).commit();
    }

    public void startTrainingGame(MainActivity mainActivity){
        mCurrentMode = "training";
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new MultiplayerMenuFragment()).commit();
    }

    //Lance le prochain jeu aléatoirement choisi
    public void nextGame(MainActivity mainActivity){

        if(mCurrentMode == "solo" && currentGameIndex != NB_GAMES_TO_PLAY - 1){
            currentGameIndex++;
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, gamesOrder.get(currentGameIndex)).commit();
        }else{
            endGame(mainActivity);
        }
    }

    public void endGame(AppCompatActivity mainActivity){
        if(mCurrentMode == "solo"){
            gamesFragment = null;
            gamesOrder.clear();
            currentGameIndex = 0;
        }

        mCurrentMode = "";
        mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new EndFragment()).commit();
    }
}
