package com.iot.jeupromob.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.reflect.Array.getLength;

public class Scores {
    public ArrayList<Integer> mList;
    public static volatile Scores globalScores = new Scores(new ArrayList<Integer>());


    private Scores(ArrayList<Integer> mList){
        this.mList = mList;
    }

    public void addNew(int newScore){
        mList.add(newScore);
        Collections.sort(mList, Collections.reverseOrder());
        if(mList.size() >5)
            {mList.remove(4);}
    }

    public ArrayList<Integer> getList(){
        return globalScores.mList;
    }
}