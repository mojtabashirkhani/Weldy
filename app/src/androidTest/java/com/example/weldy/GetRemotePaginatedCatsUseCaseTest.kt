package com.example.weldy

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.repository.CatRepository
import com.example.weldy.domain.repository.paged.CatRemoteSource
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Test
    fun test_paging_contains_expected_items() = runTest {
        val pager = Pager(PagingConfig(pageSize = 10)) {
            catRemoteSource
        }

        val mockList: List<Cat> = List(16) { index -> Cat("Cat$index") }

        val flow: Flow<PagingData<Cat>> = pager.flow

        val itemsSnapshot: List<Cat> = flow.asSnapshot {
            scrollTo(index = 15)
        }

        assertThat(
            mockList,
            containsInAnyOrder(itemsSnapshot)
        )
    }

    @Test
    fun test_footer_is_visible() = runTest {
        // Get the Flow of PagingData from the ViewModel under test
        val pager = Pager(PagingConfig(pageSize = 10)) {
            catRemoteSource
        }

        val flow: Flow<PagingData<Cat>> = pager.flow

        val itemsSnapshot: List<Cat> = flow.asSnapshot {
            appendScrollWhile {item: Cat -> item != Cat("Cat99")}
        }

        assertThat(itemsSnapshot, hasItem(equalTo("Cat99")))
    }

}