package com.example.weldy.di

import com.example.weldy.FakeCatRepository
import com.example.weldy.domain.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestCatModule {
    @Provides
    @Singleton
    fun provideFakeCatRepository(): CatRepository = FakeCatRepository()
}
