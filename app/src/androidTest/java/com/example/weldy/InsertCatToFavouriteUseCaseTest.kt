package com.example.weldy

import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.domain.usecase.InsertCatToFavouriteUseCase
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class InsertCatToFavouriteUseCaseTest {
    private val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)
    private val insertCatToFavouriteUseCase = InsertCatToFavouriteUseCase(catRepository)

    @Test
    fun  insertCatToFavourite() = runTest {
        val catEntity = CatEntity(id = "Cat1", url = "http://example.com/cat1.jpg")

        insertCatToFavouriteUseCase(catEntity)

        verify(catRepository).insertCatToFavourite(catEntity)
    }
}