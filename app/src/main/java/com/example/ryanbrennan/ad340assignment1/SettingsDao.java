package com.example.ryanbrennan.ad340assignment1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ryanbrennan.ad340assignment1.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM settings")
    List<Settings> getAll();

    @Query("SELECT * FROM settings WHERE email LIKE :email")
    List<Settings> findByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Settings... settings);

    @Delete
    void delete(Settings settings);

}
