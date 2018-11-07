package com.iot.jeupromob;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PetitBac extends AppCompatActivity {
    private TableLayout tableLayout;
    private TableRow[] tableRows;

    public PetitBac() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petit_bac);

        tableLayout = (TableLayout) findViewById(R.id.TableLayoutPetitBac);

        setPlayersGrid();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_history, container, false);
//    }

    private void setPlayersGrid(){
        for(int i=0; i < GameManager.getInstance().nbPlayers; i++){
            //TableRow row = new TableRow.LayoutParams();

        }
    }
}
