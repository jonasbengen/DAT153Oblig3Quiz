package com.example.oblig1quiz.DAO;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.oblig1quiz.Util.PhotoInfo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("DELETE from gallery")
    void deleteAll();

    @Insert
    void addPhoto();

    @Delete
    void deletePhoto();

    @Update
    void updatePhoto();

    @Query("SELECT * FROM gallery")
    List<PhotoInfo> selectAll();

    // TODO
    @Query("")
    PhotoInfo getPhoto();

}