package com.example.ryanbrennan.ad340assignment1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class AppDatabaseSingleton {
    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "sample-database").build();
//                    .addMigrations(MIGRATION_2_3)

        }

        return db;
    }

//    private static final Migration MIGRATION_2_3  = new Migration(2,3){
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database){
//            database.execSQL("ALTER TABLE settings ADD COLUMN reminder_time TEXT");
//            database.execSQL("ALTER TABLE settings ADD COLUMN match_distance_search INTEGER");
//            database.execSQL("ALTER TABLE settings ADD COLUMN gender TEXT");
//            database.execSQL("ALTER TABLE settings ADD COLUMN privacy BIT default 'FALSE'");
//            database.execSQL("ALTER TABLE settings ADD COLUMN lower_age_range INTEGER");
//            database.execSQL("ALTER TABLE settings ADD COLUMN upper_age_range INTEGER");
//        }
//    };
}
