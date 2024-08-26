package com.example.weldy.di

import com.example.weldy.data.mapper.CatMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatMapperModule {
    @Provides
    @Singleton
    fun provideCatMapper(): CatMapper {
        return CatMapper()
    }
}