package com.example.weldy.di

import android.app.Application
import androidx.room.Room
import com.example.weldy.DB_NAME
import com.example.weldy.data.local.CatDatabase
import com.example.weldy.data.local.dao.CatDao
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
    fun provideRoomDatabase(application: Application): CatDatabase {
        return Room
            .databaseBuilder(application, CatDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCatDao(db: CatDatabase): CatDao {
        return db.catDao()
    }
}