package com.aks.expencetracker.repositories.databases;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aks.expencetracker.models.database_models.MainDao;

public abstract class RoomDB extends RoomDatabase {
    private static final String DATABASE_NAME = "database";
    private static RoomDB database;

    public static synchronized RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MainDao mainDao();

}
