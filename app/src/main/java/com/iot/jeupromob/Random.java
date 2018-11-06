package com.iot.jeupromob;

import android.util.Log;

import java.util.ArrayList;


//Class pour aider à générer de l'aléatoire, mélanger aléatoirement un ArrayList, etc...

public class Random {

    //Retourne une version mélangée de la liste donnée
    public static ArrayList shuffleArrayList(ArrayList list){
        ArrayList copy = new ArrayList();
        ArrayList result = new ArrayList();

        //On copie la liste donnée pour ne pas la modifier
        for(int i=0; i < list.size(); i++){
            copy.add(list.get(i));
        }

        Log.d("List.size()", "=" + list.size());

        //Puis on la mélange
        for(int i=0; i < list.size(); i++){
            int random = (int) Math.round(Math.random() * (list.size() - 1 - i));
            result.add(copy.get(random));
            copy.remove(random);
        }

        return result;
    }

    //Garanti d'avoir une chance égale entre toutes les valeurs de la range
    public static int randomInt(int range){
        boolean isResultValid = true;
        int result = 0;

        //On exclu les valeurs extrêmes de la range car elles ont 2 fois moins de chance de sortir que les autres
        while(!isResultValid){
            int random = (int) Math.round(Math.random() * (range+2) );
            if(random == 0 || random == range + 2){
                isResultValid = false;
            }else{
                isResultValid = true;
            }
        }

        return result - 1;
    }
}
