package com.aks.expencetracker.di;

import android.content.Context;

import com.aks.expencetracker.utils.databases.ExpenseTableDao;
import com.aks.expencetracker.utils.databases.RoomDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MainModuleApp {
    @Provides
    @Singleton
    public RoomDB provideDataBase(@ApplicationContext Context context) {
        return RoomDB.getInstance(context);
    }

    @Provides
    @Singleton
    public ExpenseTableDao providesProfileDetailsDao(RoomDB roomDbMain) {
        return roomDbMain.mainDao();
    }
}
