package com.example.weldy

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.paging.testing.asSnapshot
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.repository.CatRepository
import com.example.weldy.domain.repository.paged.CatRemoteSource
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertNull
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.logging.Level.CONFIG
import javax.inject.Inject

@HiltAndroidTest
class GetRemotePaginatedCatsUseCaseTest {

    // Hilt rule to manage injection
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Inject the CatRepository and the GetRemotePaginatedCatsUseCase
    @BindValue
    val catRepository: CatRepositoryImpl = mock(CatRepositoryImpl::class.java)

    @Inject
    lateinit var getRemotePaginatedCatsUseCase: GetRemotePaginatedCatsUseCase

    @Inject
    lateinit var fakeApi: FakeCatApi

    private lateinit var catRemoteSource: CatRemoteSource

    private lateinit var fakeCatRepository: FakeCatRepository

    private lateinit var mockCats: List<CatResponse>


    @Before
    fun setup() {
        // Inject the dependencies before running the tests
        hiltRule.inject()
        catRemoteSource = CatRemoteSource(getRemotePaginatedCatsUseCase)
        fakeCatRepository = FakeCatRepository()


        mockCats = listOf(
            CatResponse(id = "1", url = "https://example.com/cat1.jpg"),
            CatResponse(id = "2", url = "https://example.com/cat2.jpg"),
            CatResponse(id = "3", url = "https://example.com/cat3.jpg")
        )

        // Add mock responses to the fake API
        mockCats.forEach { cat -> fakeApi.addCatResponse(cat) }
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
            FakeCatPagingSource(fakeCatRepository)
        }

        val flow: Flow<PagingData<Cat>> = pager.flow

        val itemsSnapshot: List<Cat> = flow.asSnapshot {
            appendScrollWhile {item: Cat -> item != Cat("Cat99")}
        }

        assertThat(itemsSnapshot, hasItem(equalTo("Cat99")))
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        // Create the CatPagingSource using the injected fake API
        val pagingSource = FakeApiCatPagingSource(
            fakeApi,
            limit = 3,
        )

        val pager = TestPager(PagingConfig(0), pagingSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        // Write assertions against the loaded data
        for (i in mockCats.indices) {
            assertEquals(mockCats[i], result.data[i])
        }
    }


    @Test
    fun test_consecutive_loads() = runTest {


        // Create the CatPagingSource using the injected fake API
        val pagingSource = FakeApiCatPagingSource(
            fakeApi,
            limit = 3,
        )

        val pager = TestPager(PagingConfig(0), pagingSource)

        val page = with(pager) {
            refresh()
            append()
            append()
        } as PagingSource.LoadResult.Page

        for (i in mockCats.indices) {
            assertEquals(mockCats[i], page.data[i])
        }
    }

    @Test
    fun refresh_returnError() {
        val pagingSource = FakeApiCatPagingSource(
            fakeApi,
            limit = 3
        )
        // Configure your fake to return errors
        fakeApi.setReturnsError(true)
        val pager = TestPager(PagingConfig(0), pagingSource)

        runTest {
            pagingSource.errorNextLoad = true
            val result = pager.refresh()
            assertTrue(result is PagingSource.LoadResult.Error)

            val page = pager.getLastLoadedPage()
            assertNull(page)
        }
    }

}