package com.example.weldy

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.repository.CatRepository
import com.example.weldy.domain.repository.paged.CatRemoteSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import javax.inject.Inject

@HiltAndroidTest
class GetRemotePaginatedCatsUseCaseTest {

    // Hilt rule to manage injection
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Inject the CatRepository and the GetRemotePaginatedCatsUseCase
    @Inject
    lateinit var catRepository: CatRepository

    @Inject
    lateinit var getRemotePaginatedCatsUseCase: GetRemotePaginatedCatsUseCase

    private lateinit var catRemoteSource: CatRemoteSource

    @Before
    fun setup() {
        // Inject the dependencies before running the tests
        hiltRule.inject()
        catRemoteSource = CatRemoteSource(getRemotePaginatedCatsUseCase)
    }

    @Test
    fun getCatsRemote() = runTest {
        val mockResponse = listOf(
            Cat("Cat1"),
            Cat("Cat2")
        )

        // Mock the behavior of the repository to return the mock response
        `when`(catRepository.getCatsRemote(10, 1)).thenReturn(mockResponse)

        // Call the use case
        val result = getRemotePaginatedCatsUseCase(10, 1)

        // Verify the results
        assertEquals(mockResponse, result)
        verify(catRepository).getCatsRemote(10, 1)
    }

    /*@Test
    fun test_paging_contains_expected_items() = runTest {
        val pager = Pager(PagingConfig(pageSize = 10)) {
            catRemoteSource
        }


        val flow: Flow<PagingData<CatResponse>> = pager.flow

        val itemsSnapshot: List<CatResponse> = flow.asSnapshot {
            scrollTo(index = 15)
        }

        assertEquals(
            expected = List(16) { index -> CatResponse("Cat$index") },
            actual = itemsSnapshot
        )
    }*/
}