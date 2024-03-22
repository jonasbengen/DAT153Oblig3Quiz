package com.example.oblig1quiz.Gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.oblig1quiz.Database.PhotoInfoAdapter;
import com.example.oblig1quiz.Database.PhotoViewModel;
import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.Datamanager;
import com.example.oblig1quiz.Util.PhotoInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
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
        setContentView(R.layout.activity_gallery);

        linearLayoutManager = new LinearLayoutManager(this);

        imagesView = findViewById(R.id.recyclerView);
        adapter = new PhotoInfoAdapter(new PhotoInfoAdapter.PhotoDiff());

        imagesView.setAdapter(adapter);
        imagesView.setLayoutManager(linearLayoutManager);

/*
        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        } else {
            // Permission is granted, you can access images
            // Now you can proceed to access images from the camera roll/gallery
            photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

            photoViewModel.getAllPhotos().observe(this, photos -> {
                // Update the cached copy of the photos in the adapter.
                adapter.submitList(photos);
            });
        }*/
        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        photoViewModel.getAllPhotos().observe(this, photos -> {
            // Update the cached copy of the photos in the adapter.
            adapter.submitList(photos);
        });

        FloatingActionButton sortButton = findViewById(R.id.sortButton);

        // New list and checking if sorted allready
        //photolist = datamanager.getPhotolist();
        //sortedAlfabetical = checkForSorted(photolist);

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
            // Update screen
            imagesView.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();

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
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            // Check if permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can access images
                // Now you can proceed to access images from the camera roll/gallery
                photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);

                photoViewModel.getAllPhotos().observe(this, photos -> {
                    // Update the cached copy of the photos in the adapter.
                    adapter.submitList(photos);
                });
            } else {
                // Permission denied, handle this scenario appropriately (e.g., show a message to the user)
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("This app needs access to images to function properly. Please grant permission to continue.");
        builder.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Request permission again
                ActivityCompat.requestPermissions(GalleryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Permission denied, handle this scenario appropriately (e.g., show a message to the user)
                Toast.makeText(GalleryActivity.this, "Permission denied to images", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false); // Prevent dialog from being dismissed by tapping outside
        builder.show();
    }*/

    // Function to check if the list is sorted
    private boolean checkForSorted(List<PhotoInfo> photolist) {
        List<PhotoInfo> tmp = new ArrayList<>(photolist);
        tmp.sort(Comparator.comparing(PhotoInfo::getName));
        return tmp.equals(photolist);
    }

}