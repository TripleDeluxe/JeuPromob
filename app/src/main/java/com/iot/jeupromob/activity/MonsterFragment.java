package com.iot.jeupromob.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iot.jeupromob.util.Monster;
import com.iot.jeupromob.util.MonsterView;

import org.xmlpull.v1.XmlPullParser;

import static com.iot.jeupromob.util.Monster.globalMonster;


public class MonsterFragment extends Fragment {

    int i = 0;
    /** diametre du monstre*/
    public static final int DOT_DIAMETER = 6;
    /** score et timer*/
    public int timeremaining = 30;
    public static int score = 0;
    /**
     * prend en parametre le monstre
     * récupere la position du doigt
     * la compare avec celle du monstre
     * donne un bonus ou un malus
     * recréé un monstre random en remplacant le monstre actuel
     * On modifie donc pas le parametre myMonster mais l'instance globalMonster
     *
     * */
    private class TrackingTouchListener implements View.OnTouchListener {

        private Monster myMonster;
        int track;

        TrackingTouchListener() { myMonster = globalMonster; }
        private MonsterView monsterViewz;

        @Override public boolean onTouch(final View v, final MotionEvent evt) {
            final int action = evt.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    final int idx1 = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    track = (evt.getPointerId(idx1));
                    break;
                default:
                    return false;
            }
            /** ANALYSE ET COMPARAISON
            Probleme: les valeurs sont en float, elle ne seront quasiment jamais similaires
            Solution: arrondir
            Probleme2: 1,49 ~ 1,51 or l'arrondi donnera 1 et 2
            Solution2: on fait une fraction, si le restultat est proche de 1, la position est validée
            */

            //fraction
            double fractionX = evt.getX(track) / (v.getWidth() / 10);
            //Arrondi du float avec floor puis cast en int
            int compX = (int)Math.floor(fractionX);

            //pareil en y
            double fractionY = evt.getY(track) / (v.getHeight() / 10);
            int compY = (int)Math.floor(fractionY);

            if((compX==1)&&(compY==1)){
                if (myMonster.getColor()==0){
                    score-=5;
                }
                else{
                    score++;
                }
                //pause de 0 à 300 ms avant la création d'un monstre
                //long slep = (long)Math.floor(Math.random()*300);
                //wait(slep);
                globalMonster.changeMonster(getActivity().getWindowManager().getDefaultDisplay().getWidth(),getActivity().getWindowManager().getDefaultDisplay().getHeight());

            }
            else{
                //pause de 0 à 300 ms avant la création d'un monstre
                //long slep = (long)Math.floor(Math.random()*300);
                //wait(slep);

                globalMonster.changeMonster(getActivity().getWindowManager().getDefaultDisplay().getWidth(),getActivity().getWindowManager().getDefaultDisplay().getHeight());

            }

            return true;
        }

    }




    public void onCreate(Bundle state) {
        super.onCreate(state);
        globalMonster.changeMonster(getActivity().getWindowManager().getDefaultDisplay().getWidth(),getActivity().getWindowManager().getDefaultDisplay().getHeight());


    }

    //la view sajoute dans oncreateview car il s'agit d'un fragment, non d'une activité
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate((XmlPullParser) new MonsterView(this.getContext()), container, false);
        //n'accepte que le .xml alors on cast
        return rootView;
    }

}


    /** Called when the activity is first created.
    @Override public void onCreate(final Bundle state) {
        super.onCreate(state);

        // install the view
        setContentView(R.layout.main);

        // find the monsters view
        monsterView = (MonsterView) findViewById(R.id.monsters);
        monsterView.setMonsters(monsterModel);

        monsterView.setOnCreateContextMenuListener(this);
        monsterView.setOnTouchListener(new TrackingTouchListener(monsterModel));

        monsterView.setOnKeyListener((final View v, final int keyCode, final KeyEvent event) -> {
            if (KeyEvent.ACTION_DOWN != event.getAction()) {
                return false;
            }
            return true;
        });
        //Links to the XML for buttons
        final EditText tb1 = (EditText) findViewById(R.id.text1);
        final EditText tb2 = (EditText) findViewById(R.id.text2);
        final EditText tb3 = (EditText) findViewById(R.id.text3);
        monsterModel.setMonstersChangeListener((Monsters monsters) -> {
            tb1.setText("Score: " + score);
            tb2.setText("Time Left: " + timeremaining);
            tb3.setText("Level: " + level);
            monsterView.invalidate();
        });

        findViewById(R.id.button1).setOnClickListener((final View v) ->
                onPause()
        );
        findViewById(R.id.button2).setOnClickListener((final View v) ->
                onResume()
        );

    }
     */


