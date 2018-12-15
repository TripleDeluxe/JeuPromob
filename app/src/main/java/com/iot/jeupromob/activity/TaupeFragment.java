package com.iot.jeupromob.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import com.iot.jeupromob.R;
import com.iot.jeupromob.util.Taupe;
import com.iot.jeupromob.util.TaupeView;
import static com.iot.jeupromob.util.Taupe.globalTaupe;





public class TaupeFragment extends Fragment {

    /**taille de la zone d'apparition des points definie à on create*/
    public static int layWidth;
    public static int layHeight;

    /** score et timer*/
    public CountDownTimer tiktak;
    public TextView timeText;
    public static int score = 0;
    public TextView scoreText;
    public TextView passText;

    private Button passer;

    /**
     * prend en parametre le monstre
     * récupere la position du doigt
     * la compare avec celle du monstre
     * donne un bonus ou un malus
     * recréé un monstre random en remplacant le monstre actuel
     * On modifie donc pas le parametre myTaupe mais l'instance globalTaupe
     *
     * */
    private class TrackingTouchListener implements View.OnTouchListener {

        private Taupe myTaupe;
        int track;

        @SuppressLint("SetTextI18n")
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
                if (myTaupe.getColor()==0){
                    score-=6;
                    scoreText.setText(score);
                }
                else{
                    score++;
                    scoreText.setText(score);
                }
                //pause de 0 à 300 ms avant la création d'un monstre
                //long slep = (long)Math.floor(Math.random()*300);
                //wait(slep);
                globalTaupe.changeTaupe(layWidth,layHeight);
            }
            else{
                //pause de 0 à 300 ms avant la création d'un monstre
                //long slep = (long)Math.floor(Math.random()*300);
                //wait(slep);

                globalTaupe.changeTaupe(layWidth,layHeight);
            }

            return true;
        }
    }


    public void onCreate(Bundle state) {
        super.onCreate(state);
        scoreText = getView().findViewById(R.id.scoreTaupe);
        timeText = getView().findViewById(R.id.timeTaupe);
        passText= (TextView) getView().findViewById(R.id.frag_Taupe_pass_button);
        passer= (Button) getView().findViewById(R.id.frag_Taupe_pass_button);
        passer.setOnClickListener(btn);

        /**TIMER
         *
         * lorsque le timer est fini:
         *
         * on charge le score dans totalscore
         *
         *
         **/
        tiktak = new CountDownTimer(10 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText((int)millisUntilFinished/ 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("FINI");
                timeText.setTextColor(16719360);
                passText.setTextColor(7262720);


            }
        };

        tiktak.start();


        //on recupere la taille du layout dans lequel les points vont apparaitrent
        // ! lors de onCreate, le layout n'est pas bien formé, problemes possibles
        final LinearLayout layout = (LinearLayout) getView().findViewById(R.id.frag_Taupe_linear_layout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layWidth= layout.getMeasuredWidth();
                layHeight= layout.getMeasuredHeight();
            }
        });

        globalTaupe.changeTaupe(layWidth,layHeight);



    }

    //la view sajoute dans oncreateview car il s'agit d'un fragment, non d'une activité
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate((XmlPullParser) new TaupeView(this.getContext()), container, false);
        //n'accepte que le .xml alors on cast
        return rootView;
    }



    //switch pr ajouter des bontons au cas ou
    private View.OnClickListener btn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.frag_Taupe_pass_button:
                    pass();
                    break;
            }
        }
    };



    private void pass(){
        if(tiktak==null){
            //passer au fragment suivant
        }
    }



}



