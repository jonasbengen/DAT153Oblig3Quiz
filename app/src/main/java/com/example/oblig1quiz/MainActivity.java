package com.example.oblig1quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.Quiz.QuizActivity;
import com.example.oblig1quiz.Util.Datamanager;

public class MainActivity extends AppCompatActivity {

    private Datamanager datamanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datamanager = (Datamanager) getApplication();
        Button quizButton = findViewById(R.id.quizButton);
        Button galleryButton = findViewById(R.id.galleryButton);

        quizButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), QuizActivity.class);
            view.getContext().startActivity(intent);
        });

        galleryButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GalleryActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}