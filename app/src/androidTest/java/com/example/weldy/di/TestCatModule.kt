package com.example.weldy.di

import com.example.weldy.FakeCatRepository
import com.example.weldy.domain.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestCatModule {
    @Provides
    fun provideFakeCatRepository(): CatRepository = FakeCatRepository()
}