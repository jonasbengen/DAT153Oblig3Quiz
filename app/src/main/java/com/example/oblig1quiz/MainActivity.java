package com.example.oblig1quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.oblig1quiz.Util.PhotoInfoAdapter;
import com.example.oblig1quiz.Util.PhotoViewModel;
import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.Quiz.QuizActivity;


public class MainActivity extends AppCompatActivity {

    private PhotoInfoAdapter adapter;
    private PhotoViewModel photoViewModel;
    boolean hardmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        adapter = new PhotoInfoAdapter(new PhotoInfoAdapter.PhotoDiff());

        // Upload to adapter once pictures are loaded
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });


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
            if (adapter.getItemCount() < 3) {
                Toast.makeText(getApplicationContext(), "Not enough images to start quiz", Toast.LENGTH_LONG).show();
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