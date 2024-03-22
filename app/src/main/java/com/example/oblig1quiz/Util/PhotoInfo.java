package com.example.oblig1quiz.Util;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Generated;

// Image info class, URI and Name
@Entity(tableName = "gallery")
public class PhotoInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NotNull
    @ColumnInfo(name = "uri")
    private String uri;

    @NotNull
    @ColumnInfo(name ="name")
    private String name;

    public PhotoInfo(@NotNull String navn, @NotNull String uri) {
        this.uri = uri;
        this.name = navn;
    }

    public PhotoInfo() {}

    @NonNull
    public String getUri() {
        return uri;
    }

    public void setUri(@NonNull String uri) {
        this.uri = uri;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
