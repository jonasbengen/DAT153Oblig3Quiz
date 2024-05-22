package com.example.oblig1quiz.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.oblig1quiz.R;
import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    Class that creates the database
 */
@Database(entities = {PhotoInfo.class}, version = 1, exportSchema = false)
public abstract class PhotoInfoDatabase extends RoomDatabase {

    public abstract PhotoDao photoDao();

    private static volatile PhotoInfoDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback photoinfoCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more photos, just add them.
                PhotoDao dao = INSTANCE.photoDao();
                dao.deleteAll();

                dao.addPhoto(new PhotoInfo("Java", "android.resource://com.example.oblig1quiz/" + R.drawable.java));
                dao.addPhoto(new PhotoInfo("Ruby", "android.resource://com.example.oblig1quiz/" + R.drawable.ruby));
                dao.addPhoto(new PhotoInfo("Go", "android.resource://com.example.oblig1quiz/" + R.drawable.go));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            PhotoDao dao = INSTANCE.photoDao();
            dao.selectAll();
        }

    };

    static PhotoInfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhotoInfoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhotoInfoDatabase.class, "gallery")
                            .addCallback(photoinfoCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
