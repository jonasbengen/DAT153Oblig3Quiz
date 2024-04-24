package com.example.oblig1quiz.Quiz;

import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.PhotoInfo;
import com.example.oblig1quiz.Util.PhotoInfoAdapter;
import com.example.oblig1quiz.Util.PhotoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    PhotoInfoAdapter adapter;
    int score;
    List<PhotoInfo> list;
    List<PhotoInfo> usedPhotos;
    static PhotoInfo questionPhoto;

    ImageView image;
    static Button answer1;
    static Button answer2;
    static Button answer3;
    TextView scoreView;
    FloatingActionButton backButton;
    TextView countdownView;

    private PhotoViewModel photoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.quiz_landscape);
        } else {
            setContentView(R.layout.activity_quiz);
        }

        // Get extra and see if user selected hard mode
        boolean hardMode = getIntent().getBooleanExtra("HardMode", false);
        adapter = new PhotoInfoAdapter(new PhotoInfoAdapter.PhotoDiff());
        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);


        image = findViewById(R.id.quizImage);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        scoreView = findViewById(R.id.score);
        backButton = findViewById(R.id.backButtonQuiz);
        countdownView = findViewById(R.id.countdownView);

        if (savedInstanceState == null) {
            score = 0;
            list = null;
            usedPhotos = new ArrayList<>();
            questionPhoto = null;

            // Set the list and start the quiz once items are loaded
            photoViewModel.getAllPhotos().observe(this, photos -> {
                adapter.submitList(photos);
                list = adapter.getCurrentList();
                generateQuestion(hardMode);
            });

        } else {
            score = savedInstanceState.getInt("score");
            list = (List<PhotoInfo>) savedInstanceState.getSerializable("list");
            usedPhotos = (List<PhotoInfo>) savedInstanceState.getSerializable("usedPhotos");
            questionPhoto = savedInstanceState.getParcelable("questionPhoto");
            generateQuestion(hardMode);
        }

        backButton.setOnClickListener(view -> {
            finishQuiz();
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("score", score);
        savedInstanceState.putSerializable("list", (Serializable) list);
        savedInstanceState.putSerializable("usedPhotos", (Serializable) usedPhotos);
        savedInstanceState.putParcelable("questionPhoto", (Parcelable) questionPhoto);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
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
        if (questionPhoto == null) {
            do questionPhoto = getPhotoForQuestion();
            while(usedPhotos.contains(questionPhoto));
        }

        // Show it on screen
        image.setImageURI(Uri.parse(questionPhoto.getUri()));

        List<String> randomAnswers = new ArrayList<>();

        // Correct answer
        randomAnswers.add(questionPhoto.getName());

        // Wrong answers
        // Find the wrong answers randomly
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
        setOnClick(questionPhoto, hardMode, timer);
    }

    private void setOnClick(PhotoInfo correctAnswer, boolean hardmode, CountDownTimer timer) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable buttons after click
                answer1.setClickable(false);
                answer2.setClickable(false);
                answer3.setClickable(false);

                // Add the photo to the used list
                usedPhotos.add(correctAnswer);
                questionPhoto = null;

                // Stop previous timer
                if (hardmode) {
                    timer.cancel();
                }
                // Check if the clicked button is the right answer
                Button b = (Button) v;
                String text = b.getText().toString();
                if (text.equals(correctAnswer.getName())) {
                    score++;
                }

                // Set the right colors of the buttons according to right or wrong
                setColorRightOrWrong(correctAnswer.getName());

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

    public PhotoInfo getRightAnswer() {
        return questionPhoto;
    }

    public String[] getAllButtonTexts() {
        return new String[] {(String) answer1.getText(), (String) answer2.getText(), (String) answer3.getText()};
    }

}