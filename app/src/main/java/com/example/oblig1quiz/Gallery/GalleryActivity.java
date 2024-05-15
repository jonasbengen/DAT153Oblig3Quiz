package com.example.oblig1quiz.Gallery;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.PhotoInfo;
import com.example.oblig1quiz.Util.PhotoInfoAdapter;
import com.example.oblig1quiz.Util.PhotoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    boolean sortedAlfabetical;
    RecyclerView imagesView;
    PhotoInfoAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private PhotoViewModel photoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gallery);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.gallery_landscape);
        } else {
            setContentView(R.layout.activity_gallery);
        }

        linearLayoutManager = new LinearLayoutManager(this);

        imagesView = findViewById(R.id.recyclerView);
        adapter = new PhotoInfoAdapter(new PhotoInfoAdapter.PhotoDiff());

        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(linearLayoutManager);

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });

        FloatingActionButton sortButton = findViewById(R.id.sortButton);

        // New list and checking if sorted allready
        sortedAlfabetical = checkForSorted(adapter.getCurrentList());

        // Sort based on what the state is (A-Z or Z-A)
        sortButton.setOnClickListener(v -> {
            sort();
        });
    }



    @SuppressLint("NotifyDataSetChanged")
    private void sort() {
        List<PhotoInfo> photolist = adapter.getCurrentList();
        if (!photolist.isEmpty()) {
            // If not sorted alfabeticaly sort it alfabeticaly
            if (!sortedAlfabetical) {
                photoViewModel.getAlphabeticSorted().observe(this, photos -> {
                    adapter.submitList(photos);
                });
            }
            // If sorted alfabeticaly sort it reverse-alfabeticaly
            else {
                photoViewModel.getAlphabeticSortedDesc().observe(this, photos -> {
                    adapter.submitList(photos);
                });
            }

            // Switch the bool so you know how it is sorted
            sortedAlfabetical = !sortedAlfabetical;
        }
    }
    // To update when you navigate back to the activity
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView imagesView = findViewById(R.id.recyclerView);
        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(linearLayoutManager);
    }

    // Function to check if the list is sorted
    private boolean checkForSorted(List<PhotoInfo> photolist) {
        List<PhotoInfo> tmp = new ArrayList<>(photolist);
        tmp.sort(Comparator.comparing(PhotoInfo::getName));
        return tmp.equals(photolist);
    }

}