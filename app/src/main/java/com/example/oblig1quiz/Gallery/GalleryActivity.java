package com.example.oblig1quiz.Gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.Datamanager;
import com.example.oblig1quiz.Util.PhotoAdapter;

public class GalleryActivity extends AppCompatActivity {

    Datamanager datamanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        datamanager = (Datamanager) getApplication();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(datamanager, this);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(datamanager, this);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(new LinearLayoutManager(this));
    }

}