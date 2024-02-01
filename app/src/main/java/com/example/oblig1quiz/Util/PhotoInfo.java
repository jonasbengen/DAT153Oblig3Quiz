package com.example.oblig1quiz.Util;

import android.net.Uri;

public class PhotoInfo {

    private Uri uri;
    private String name;

    public PhotoInfo(String navn, Uri uri) {
        this.uri = uri;
        this.name = navn;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
