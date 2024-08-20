package com.example.weldy

import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetPaginatedCatsUseCaseTest {

    private val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)
    private val getRemotePaginatedCatsUseCase = GetRemotePaginatedCatsUseCase(catRepository)

    @Test
    fun getCatsRemote() = runTest {
        val mockResponse = listOf(
            CatResponse("Cat1"),
            CatResponse("Cat2")
        )

        `when`(catRepository.getCatsRemote(10, 1)).thenReturn(mockResponse)

        val result = getRemotePaginatedCatsUseCase(10, 1)

        assertEquals(mockResponse, result)
        verify(catRepository).getCatsRemote(10, 1)
    }
}