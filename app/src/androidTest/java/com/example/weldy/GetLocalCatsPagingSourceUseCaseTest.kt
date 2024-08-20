package com.example.weldy

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.domain.usecase.GetLocalCatsPagingSourceUseCase
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocalCatsPagingSourceUseCaseTest {

    private val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)
    private val getLocalCatsPagingSourceUseCase = GetLocalCatsPagingSourceUseCase(catRepository)

    @Test
    fun getCatsLocal() = runTest {
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, CatEntity>

        `when`(catRepository.getCatsLocal()).thenReturn(mockPagingSource)

        val result = getLocalCatsPagingSourceUseCase()

        assertEquals(mockPagingSource, result)
        verify(catRepository).getCatsLocal()
    }
}