package com.iot.jeupromob.activity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iot.jeupromob.util.GameManager;
import com.iot.jeupromob.R;
import com.iot.jeupromob.util.Random;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizzGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizzGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizzGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MediaPlayer soundGoodAnswer = null;
    private MediaPlayer soundBadAnswer = null;
    private MediaPlayer[] sounds = new MediaPlayer[]{soundGoodAnswer, soundBadAnswer};

    private Button buttonPassGame = null;
    private Button buttonSubmit = null;
    private TextView textViewQuestion = null;
    private TextInputEditText textInput = null;

    private int nbQuestions = 2;
    private ArrayList<Question> mRandomQuestions = null;

    //Nécessaire d'initialiser à -1 (cf setNextQuestion() )
    private int currentQuestionIndex = -1;
    //answers[i] = toutes les réponses possibles de questions[i]
    private ArrayList<String[]> answers = new ArrayList<String[]>();

    private class Question{
        public String question;
        public int questionIndex;
        public Question(String question, int questionIndex){
            this.question = question;
            this.questionIndex = questionIndex;
        }
    }

    public QuizzGameFragment() {
        // Required empty public constructor
    }

    private void getAnswers(){
        answers.add(getResources().getStringArray(R.array.answers_1));
        answers.add(getResources().getStringArray(R.array.answers_2));
        answers.add(getResources().getStringArray(R.array.answers_3));
    }

    //Initialise l'Array mRandomQuestions avec un nombre nbQuestions de questions aléatoires et affiche la première question
    private void setQuestions(){
        //On copy le tableau Questions dans une arrayList
        String[] mQuestions = getResources().getStringArray(R.array.questions);
        ArrayList<String> mQuestionsArrayList = new ArrayList<>();
        for(int i=0; i<mQuestions.length; i++){
            mQuestionsArrayList.add(mQuestions[i]);
        }

        //On créer le tableau de question aléatoire a partir de mQuestionsArrayList
        mRandomQuestions = new ArrayList<Question>();

        for(int i=0; i < nbQuestions; i++){
            int randomIndex = Random.randomInt(mQuestionsArrayList.size() - 1);
            mRandomQuestions.add(new Question(mQuestionsArrayList.get(randomIndex), randomIndex));
            mQuestionsArrayList.remove(randomIndex);
        }

        setNextQuestion();
    }

    private void setNextQuestion(){
        currentQuestionIndex++;
        if(currentQuestionIndex < nbQuestions){
            textInput.setText("");
            textViewQuestion.setText(mRandomQuestions.get(currentQuestionIndex).question);
        }else{
            GameManager.getInstance().nextGame((AppCompatActivity) getActivity());
        }
    }

    private void checkAnswer(){
        String answer = textInput.getText().toString().toUpperCase().replaceAll("\\s","");
        String[] questionAnswers = answers.get(mRandomQuestions.get(currentQuestionIndex).questionIndex);
        boolean isAnswerValid = false;

//        Vérification de la réponse
        for(int i=0; i < questionAnswers.length; i++){

            if(answer.equals(questionAnswers[i].toUpperCase().replaceAll("\\s",""))){
                isAnswerValid = true;
            }
        }

        if(isAnswerValid){
            soundGoodAnswer.start();
            soundGoodAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setNextQuestion();
                }
            });
        }else{
            soundBadAnswer.start();
            soundBadAnswer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    setNextQuestion();
                }
            });
        }


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizzGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizzGameFragment newInstance(String param1, String param2) {
        QuizzGameFragment fragment = new QuizzGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        soundGoodAnswer = MediaPlayer.create(getContext(), R.raw.bonne_reponse);
        soundBadAnswer = MediaPlayer.create(getContext(), R.raw.mauvaise_reponse);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizz_game, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        buttonSubmit = getView().findViewById(R.id.fragment_quizz_button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        buttonPassGame = getView().findViewById(R.id.fragment_quizz_pass_button2);
        buttonPassGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.getInstance().nextGame((AppCompatActivity) getActivity());
            }
        });

        textViewQuestion = getView().findViewById(R.id.fragment_quizz_text_view_question);
        textInput = getView().findViewById(R.id.fragment_quizz_text_input);

        getAnswers();
        setQuestions();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        //On libère les MediaPlayer des bruitages
        for(int i=0; i < sounds.length; i++){
            if(sounds[i] != null){
                sounds[i].release();
                sounds[i] = null;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
