package com.example.oblig1quiz.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhotoViewModel extends AndroidViewModel {
    private PhotoInfoRepo pRepository;

    private final LiveData<List<PhotoInfo>> allPhotos;

    public PhotoViewModel (Application application) {
        super(application);
        pRepository = new PhotoInfoRepo(application);
        allPhotos = pRepository.getAllPhotos();
    }

    public LiveData<List<PhotoInfo>> getAllPhotos() { return allPhotos; }

    public void insert(PhotoInfo photo) { pRepository.addPhoto(photo); }

    public void delete(PhotoInfo photo) {pRepository.delete(photo);}

    public PhotoInfo getPhoto(int id) throws ExecutionException, InterruptedException {return pRepository.getPhoto(id);}

    public PhotoInfo getPhotoAtPosition(int position) {
        return allPhotos.getValue().get(position);
    }

    public LiveData<List<PhotoInfo>> getAlphabeticSorted() {
        return pRepository.getAlphabeticSorted();
    }

    public LiveData<List<PhotoInfo>> getAlphabeticSortedDesc() {
        return pRepository.getAlphabeticSortedDesc();
    }
}
