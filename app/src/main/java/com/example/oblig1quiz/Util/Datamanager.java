package com.example.oblig1quiz.Util;

import android.app.Application;
import android.net.Uri;

import com.example.oblig1quiz.R;

import java.util.ArrayList;
import java.util.List;

public class Datamanager extends Application {

    private List<PhotoInfo> photolist;
    @Override
    public void onCreate() {
        super.onCreate();

        photolist = new ArrayList<>();
        photolist.add(new PhotoInfo("Java", "android.resource://com.example.oblig1quiz/" + R.drawable.java));
        photolist.add(new PhotoInfo("Ruby", "android.resource://com.example.oblig1quiz/" + R.drawable.ruby));
        photolist.add(new PhotoInfo("Go", "android.resource://com.example.oblig1quiz/" + R.drawable.go));
    }

    public void add(PhotoInfo photo) {
        photolist.add(photo);
    }

    public void remove(int position) {photolist.remove(position);}

    public List<PhotoInfo> getPhotolist() {return photolist;}
}
