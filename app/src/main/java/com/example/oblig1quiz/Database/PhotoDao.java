package com.example.oblig1quiz.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.List;

/*
    Class that have methods to talk with the database
 */
@Dao
public interface PhotoDao {

    @Query("DELETE FROM gallery")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPhoto(PhotoInfo photo);


    @Delete
    void deletePhoto(PhotoInfo photo);

    @Update
    void updatePhoto(PhotoInfo photo);

    @Query("SELECT * FROM gallery")
    LiveData<List<PhotoInfo>> selectAll();


    @Query("SELECT * FROM gallery WHERE id = :id")
    PhotoInfo getPhoto(int id);

    @Query("SELECT * FROM gallery ORDER BY name ASC")
    LiveData<List<PhotoInfo>> getAlphabeticSorted();

    @Query("SELECT * FROM gallery ORDER BY name DESC")
    LiveData<List<PhotoInfo>> getAlphabeticSortedDesc();

}