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


public class QuizzGameFragment extends Fragment {

    private MediaPlayer soundGoodAnswer = null;
    private MediaPlayer soundBadAnswer = null;
    private MediaPlayer[] sounds = new MediaPlayer[]{soundGoodAnswer, soundBadAnswer};

    private Button buttonSubmit = null;
    private TextView textViewQuestion = null;
    private TextInputEditText textInput = null;

    private int nbQuestions = 2;
    private ArrayList<Question> mRandomQuestions = null;

    //Nécessaire d'initialiser à -1 (cf setNextQuestion() )
    private int currentQuestionIndex = -1;
    //answers[i] = toutes les réponses possibles de questions[i]
    private ArrayList<String[]> answers = new ArrayList<String[]>();

    //Contient la String d'une question et son index (dans l'ordre d'apparition du fichier strings.xml)
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
        //On copy le tableau Questions dans une arrayList de Question
        String[] mQuestions = getResources().getStringArray(R.array.questions);
        ArrayList<Question> mQuestionsArrayList = new ArrayList<>();
        for(int i=0; i<mQuestions.length; i++){
            mQuestionsArrayList.add(new Question(mQuestions[i], i));
        }

        //On créer le tableau de question aléatoire a partir de mQuestionsArrayList
        mRandomQuestions = new ArrayList<Question>();
        for (int i = 0; i < nbQuestions; i++) {
            int randomIndex = Random.randomNumber(mQuestionsArrayList.size() - 1);
            mRandomQuestions.add(mQuestionsArrayList.get(randomIndex));
            mQuestionsArrayList.remove(i);
        }

        setNextQuestion();
    }

    private void setNextQuestion(){
        currentQuestionIndex++;
        if(currentQuestionIndex < nbQuestions){
            textInput.setText("");
            textViewQuestion.setText(mRandomQuestions.get(currentQuestionIndex).question);
        }else{
            GameManager.getInstance().nextGame((MainActivity) getActivity());
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        textViewQuestion = getView().findViewById(R.id.fragment_quizz_text_view_question);
        textInput = getView().findViewById(R.id.fragment_quizz_text_input);

        getAnswers();
        setQuestions();
    }

}
