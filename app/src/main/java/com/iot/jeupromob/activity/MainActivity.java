package com.iot.jeupromob.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iot.jeupromob.R;

public class MainActivity extends AppCompatActivity implements PetitBacFragment.OnFragmentInteractionListener,
        BeerGameFragment.OnFragmentInteractionListener, QuizzGameFragment.OnFragmentInteractionListener,
        GameMenuFragment.OnFragmentInteractionListener, EndFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new GameMenuFragment()).commit();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
