package com.example.oblig1quiz.Gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.Datamanager;
import com.example.oblig1quiz.Util.PhotoAdapter;
import com.example.oblig1quiz.Util.PhotoInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    Datamanager datamanager;
    boolean sortAlfabetical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        datamanager = (Datamanager) getApplication();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(datamanager, this);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton sortButton = findViewById(R.id.sortButton);

        // New list and checking for sorted allready
        List<PhotoInfo> photolist = datamanager.getPhotolist();
        List<PhotoInfo> tmp = new ArrayList<>(photolist);
        Collections.sort(tmp, Comparator.comparing(PhotoInfo::getName));
        sortAlfabetical = tmp.equals(photolist);

        // Sort based on what the state is (A-Z or Z-A)
        sortButton.setOnClickListener(view -> {
            if (!photolist.isEmpty()) {
                if (!sortAlfabetical) {
                    Collections.sort(photolist, Comparator.comparing(PhotoInfo::getName));
                } else {
                    Collections.sort(photolist, Comparator.comparing(PhotoInfo::getName, Collections.reverseOrder()));
                }
                //imagesView.setAdapter(adapter);
                imagesView.setLayoutManager(new LinearLayoutManager(this));
                adapter.notifyDataSetChanged();
                sortAlfabetical = !sortAlfabetical;
            }
        });
    }

    // To update when you navigate back
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(datamanager, this);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(new LinearLayoutManager(this));
    }

}