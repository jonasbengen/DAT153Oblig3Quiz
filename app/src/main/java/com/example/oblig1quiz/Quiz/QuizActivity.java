package com.example.oblig1quiz.Quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oblig1quiz.Database.PhotoInfoAdapter;
import com.example.oblig1quiz.Database.PhotoViewModel;
import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.Datamanager;
import com.example.oblig1quiz.Util.PhotoInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    Datamanager datamanager;
    PhotoInfoAdapter adapter;
    int score;
    List<PhotoInfo> list;
    List<PhotoInfo> usedPhotos;

    ImageView image;
    Button answer1;
    Button answer2;
    Button answer3;
    TextView scoreView;
    FloatingActionButton backButton;
    TextView countdownView;

    private PhotoViewModel photoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get extra and see if user selected hard mode
        boolean hardMode = getIntent().getBooleanExtra("HardMode", false);
        datamanager = (Datamanager) getApplication();

        usedPhotos = new ArrayList<>();
        score = 0;


        list = datamanager.getPhotolist(); //adapter.getCurrentList();
        image = findViewById(R.id.quizImage);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        scoreView = findViewById(R.id.score);
        backButton = findViewById(R.id.backButtonQuiz);
        countdownView = findViewById(R.id.countdownView);

        backButton.setOnClickListener(view -> {
            finishQuiz();
        });

        generateQuestion(hardMode);
    }

    // Generate a new question to show on screen
    private void generateQuestion(boolean hardMode) {
        // Setup timer if hardmode
        CountDownTimer timer = null;
        if (hardMode) {
            timer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    countdownView.setText("" + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    generateQuestion(hardMode);
                }
            }.start();
        }
        answer1.setBackgroundColor(Color.parseColor("#FF673AB7"));
        answer2.setBackgroundColor(Color.parseColor("#FF673AB7"));
        answer3.setBackgroundColor(Color.parseColor("#FF673AB7"));

        // Set the score on screen
        scoreView.setText("Score: " + score + " / " + usedPhotos.size());

        if (usedPhotos.size() == list.size()) {
            finishQuiz();
            return;
        }

        // Loop to find questions that is not used
        PhotoInfo questionPhoto;
        do questionPhoto = getPhotoForQuestion();
        while(usedPhotos.contains(questionPhoto));

        // Add it to used Photos and show it on screen
        usedPhotos.add(questionPhoto);
        image.setImageURI(Uri.parse(questionPhoto.getUri()));

        List<String> randomAnswers = new ArrayList<>();

        // Correct answer
        randomAnswers.add(questionPhoto.getName());

        // Wrong answers
        // Find to wrong answers randomly
        for (int i = 0; i < 2; i++) {
            String answer = getRandomAnswers(randomAnswers);
            randomAnswers.add(answer);
        }

        // Shuffle to not get the right answer on the first button every time
        Collections.shuffle(randomAnswers);

        answer1.setText(randomAnswers.get(0));
        answer2.setText(randomAnswers.get(1));
        answer3.setText(randomAnswers.get(2));

        // Set onclick for the buttons
        setOnClick(questionPhoto.getName(), hardMode, timer);
    }

    private void setOnClick(String correctAnswer, boolean hardmode, CountDownTimer timer) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable buttons after click
                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);

                // Stop previous timer
                if (hardmode) {
                    timer.cancel();
                }
                // Check if the clicked button is the right answer
                Button b = (Button) v;
                String text = b.getText().toString();
                if (text.equals(correctAnswer)) {
                    score++;
                }

                // Set the right colors of the buttons according to right or wrong
                setColorRightOrWrong(correctAnswer);

                // Delay before showing next question
                Handler handler = new Handler();
                 // wait 3000ms
                handler.postDelayed(new Runnable(){
                    public void run(){
                        generateQuestion(hardmode);
                    }
                }, 3000);
            }

        };
        answer1.setOnClickListener(onClickListener);
        answer2.setOnClickListener(onClickListener);
        answer3.setOnClickListener(onClickListener);
    }

    // Get a random photo
    private PhotoInfo getPhotoForQuestion() {
        Random rand = new Random();
        PhotoInfo questionPhoto = list.get(rand.nextInt(list.size()));
        return questionPhoto;
    }

    // Get random answers that is not in the list
    private String getRandomAnswers(List<String> randomAnswers) {
        Random rand = new Random();
        String answer;
        do answer = list.get(rand.nextInt(list.size())).getName();
        while(randomAnswers.contains(answer));
        return answer;
    }

    // Finish the quiz
    private void finishQuiz() {
        finish();
        Toast.makeText(getApplicationContext(), "You got " + score + " points out of " + usedPhotos.size(), Toast.LENGTH_LONG).show();
    }

    // Set colors according to right or wrong
    private void setColorRightOrWrong(String correctAnswer) {
        String color = (answer1.getText().equals(correctAnswer)) ? "green" : "red";
        answer1.setBackgroundColor(Color.parseColor(color));

        color = (answer2.getText().equals(correctAnswer)) ? "green" : "red";
        answer2.setBackgroundColor(Color.parseColor(color));

        color = (answer3.getText().equals(correctAnswer)) ? "green" : "red";
        answer3.setBackgroundColor(Color.parseColor(color));
    }

}