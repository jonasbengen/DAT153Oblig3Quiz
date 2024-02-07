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
    boolean sortedAlfabetical;

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

        // New list and checking if sorted allready
        List<PhotoInfo> photolist = datamanager.getPhotolist();
        sortedAlfabetical = checkForSorted(photolist);

        // Sort based on what the state is (A-Z or Z-A)
        sortButton.setOnClickListener(view -> {
            if (!photolist.isEmpty()) {

                // If not sorted alfabeticaly sort it alfabeticaly
                if (!sortedAlfabetical) {
                    Collections.sort(photolist, Comparator.comparing(PhotoInfo::getName));
                }
                // If sorted alfabeticaly sort it reverse-alfabeticaly
                else {
                    Collections.sort(photolist, Comparator.comparing(PhotoInfo::getName, Collections.reverseOrder()));
                }
                // Update screen
                imagesView.setLayoutManager(new LinearLayoutManager(this));
                adapter.notifyDataSetChanged();

                // Switch the bool so you know how it is sorted
                sortedAlfabetical = !sortedAlfabetical;
            }
        });
    }

    // To update when you navigate back to the activity
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(datamanager, this);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Function to check if the list is sorted
    private boolean checkForSorted(List<PhotoInfo> photolist) {
        List<PhotoInfo> tmp = new ArrayList<>(photolist);
        Collections.sort(tmp, Comparator.comparing(PhotoInfo::getName));
        return tmp.equals(photolist);
    }

}