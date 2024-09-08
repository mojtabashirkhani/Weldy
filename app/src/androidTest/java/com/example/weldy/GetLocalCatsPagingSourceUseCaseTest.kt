package com.example.weldy

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.domain.usecase.GetLocalCatsPagingSourceUseCase
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.repository.CatRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class GetLocalCatsPagingSourceUseCaseTest {

    // Hilt rule to inject dependencies
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Inject the FakeCatRepository and the use case under test
    @BindValue
    val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)

    @Inject
    lateinit var getLocalCatsPagingSourceUseCase: GetLocalCatsPagingSourceUseCase

    @Before
    fun setup() {
        // Inject the dependencies before running the tests
        hiltRule.inject()
    }

    @Test
    fun getCatsLocal() = runTest {
        // Create a mock PagingSource
        val mockPagingSource = mock(PagingSource::class.java) as PagingSource<Int, CatEntity>

        // Mock the behavior of the repository
        `when`(catRepository.getCatsLocal()).thenReturn(mockPagingSource)

        // Call the use case
        val result = getLocalCatsPagingSourceUseCase()

        // Verify the results
        assertEquals(mockPagingSource, result)
        verify(catRepository).getCatsLocal()
    }
}