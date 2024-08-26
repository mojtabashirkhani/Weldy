package com.example.weldy

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.usecase.InsertCatToFavouriteUseCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class InsertCatToFavouriteUseCaseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)

    @Inject
    lateinit var insertCatToFavouriteUseCase: InsertCatToFavouriteUseCase

    @Before
    fun setUp() {
        hiltRule.inject() // Inject the dependencies using Hilt
    }

    @Test
    fun insertCatToFavourite() = runTest {
        val catEntity = Cat(id = "Cat1", url = "http://example.com/cat1.jpg")

        insertCatToFavouriteUseCase(catEntity)

        verify(catRepository).insertCatToFavourite(catEntity)
    }
}