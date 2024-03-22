package com.example.oblig1quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.oblig1quiz.Database.PhotoInfoAdapter;
import com.example.oblig1quiz.Database.PhotoViewModel;
import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.Quiz.QuizActivity;
import com.example.oblig1quiz.Util.Datamanager;
import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PhotoInfoAdapter adapter;
    private PhotoViewModel photoViewModel;
    List<PhotoInfo> list;
    boolean hardmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PhotoInfoAdapter(new PhotoInfoAdapter.PhotoDiff());

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });


        Log.d("JBE", "" + adapter.getItemCount());
        list = adapter.getCurrentList();

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
            if (list.size() < 3) {
                Toast.makeText(getApplicationContext(), "Not enough images to start quiz", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(view.getContext(), QuizActivity.class);
                intent.putExtra("HardMode", hardmode);
                view.getContext().startActivity(intent);
            }

        });

        galleryButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GalleryActivity.class);
            //view.getContext().startActivity(intent);
            startActivityForResult(intent, 0);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list = adapter.getCurrentList();
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });
        Log.d("JBE", "" + adapter.getItemCount());
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        list = adapter.getCurrentList();
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });
        Log.d("JBE", "" + adapter.getItemCount());
    }*/
}