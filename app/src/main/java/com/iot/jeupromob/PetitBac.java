package com.iot.jeupromob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PetitBac extends AppCompatActivity {
    private TableLayout tableLayout;
    private TableRow[] tableRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petit_bac);

        tableLayout = (TableLayout) findViewById(R.id.TableLayoutPetitBac);

        setPlayersGrid();
    }

    private void setPlayersGrid(){
        for(int i=0; i < GameManager.getInstance().nbPlayers; i++){
            TableRow row = new TableRow(this);

        }
    }
}
