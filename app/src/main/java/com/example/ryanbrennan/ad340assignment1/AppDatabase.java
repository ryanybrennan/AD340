package com.example.ryanbrennan.ad340assignment1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.ryanbrennan.ad340assignment1.Settings;
import com.example.ryanbrennan.ad340assignment1.SettingsDao;

@Database(entities = {Settings.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase{
    public abstract SettingsDao settingsDao();
}
