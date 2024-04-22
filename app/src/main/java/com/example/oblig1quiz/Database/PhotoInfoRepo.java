package com.example.oblig1quiz.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
    Class that binds the database and the UI
 */
public class PhotoInfoRepo {

    private PhotoDao photoDao;
    private LiveData<List<PhotoInfo>> allPhotos;


    public PhotoInfoRepo(Application application) {
        PhotoInfoDatabase db = PhotoInfoDatabase.getDatabase(application);
        photoDao = db.photoDao();
        allPhotos = photoDao.selectAll();
    }


    public LiveData<List<PhotoInfo>> getAllPhotos() {
        return allPhotos;
    }

    public LiveData<List<PhotoInfo>> getAlphabeticSorted() {
        return photoDao.getAlphabeticSorted();
    }

    public LiveData<List<PhotoInfo>> getAlphabeticSortedDesc() {
        return photoDao.getAlphabeticSortedDesc();
    }
    public void delete(PhotoInfo photo) {
        new DeletePhotoAsyncTask(photoDao).execute(photo);
    }

    public PhotoInfo getPhoto(int id) throws ExecutionException, InterruptedException {
        return new GetPhotoAsyncTask(photoDao).execute(id).get();
    }


    public void addPhoto(PhotoInfo photo) {
        new InsertPhotoAsyncTask(photoDao).execute(photo);
    }

    private static class GetPhotoAsyncTask extends AsyncTask<Integer, Void, PhotoInfo> {
        private PhotoDao dao;

        private GetPhotoAsyncTask(PhotoDao dao) {
            this.dao = dao;
        }

        @Override
        protected PhotoInfo doInBackground(Integer... id) {
            return dao.getPhoto(id[0]);
        }
    }

    private static class DeletePhotoAsyncTask extends AsyncTask<PhotoInfo, Void, Void> {
        private PhotoDao dao;

        private DeletePhotoAsyncTask(PhotoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PhotoInfo... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.deletePhoto(models[0]);
            return null;
        }
    }

    private static class InsertPhotoAsyncTask extends AsyncTask<PhotoInfo, Void, Void> {
        private PhotoDao dao;

        private InsertPhotoAsyncTask(PhotoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PhotoInfo... photo) {
            // below line is use to insert our modal in dao.
            dao.addPhoto(photo[0]);
            return null;
        }
    }
}

