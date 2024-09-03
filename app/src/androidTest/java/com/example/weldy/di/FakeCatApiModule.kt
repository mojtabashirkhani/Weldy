package com.example.weldy.di

import com.example.weldy.FakeCatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class] // Replace this with your actual module that provides CatApi
)
object FakeCatApiModule {
    @Provides
    @Singleton
    fun provideFakeCatApi(): FakeCatApi {
        return FakeCatApi()
    }
}