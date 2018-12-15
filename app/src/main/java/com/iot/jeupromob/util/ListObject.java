package com.iot.jeupromob.util;

import java.util.ArrayList;

public class ListObject {
    public final int id;
    public final String name;
    public final int level;
    public final ArrayList listO = null;
    static int totalId = 0;

    public ListObject(String name, int level, ArrayList listO)
    {
        this.id = totalId;
        totalId ++;
        this.name = name;
        this.level = level;
        //ouvrir le fichier txt
        //créer le tableau en fonction de chaque mot, séparé par entrée

    }

}
