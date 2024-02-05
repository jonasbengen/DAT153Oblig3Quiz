package com.example.oblig1quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.Quiz.QuizActivity;
import com.example.oblig1quiz.Util.Datamanager;

public class MainActivity extends AppCompatActivity {

    private Datamanager datamanager;
    boolean hardmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datamanager = (Datamanager) getApplication();
        Button quizButton = findViewById(R.id.quizButton);
        Button galleryButton = findViewById(R.id.galleryButton);
        Switch hardmodeButton = findViewById(R.id.hardModeSwitch);

        hardmodeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hardmode = isChecked;
            }
        });

        quizButton.setOnClickListener(view -> {
            if (datamanager.getPhotolist().size() < 3) {
                Toast.makeText(getApplicationContext(), "Now enough images to start quiz", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(view.getContext(), QuizActivity.class);
                intent.putExtra("HardMode", hardmode);
                view.getContext().startActivity(intent);
            }

        });

        galleryButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GalleryActivity.class);
            view.getContext().startActivity(intent);
        });
    }
}