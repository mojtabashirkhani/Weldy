package com.example.weldy.di

import android.app.Application
import androidx.room.Room
import com.example.weldy.core.DB_NAME
import com.example.weldy.data.local.AppDatabase
import com.example.weldy.data.local.dao.WeldyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWeldyDao(db: AppDatabase): WeldyDao {
        return db.weldyDao()
    }
}