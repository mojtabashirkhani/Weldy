package com.example.weldy.di

import com.example.weldy.FakeCatApi
import com.example.weldy.data.remote.api.CatApi
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
    fun provideFakeCatApi(): CatApi {
        return FakeCatApi()
    }
}