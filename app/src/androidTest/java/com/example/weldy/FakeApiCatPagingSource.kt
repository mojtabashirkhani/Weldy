package com.example.weldy

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.model.Cat

class FakeApiCatPagingSource(
    private val api: FakeCatApi,
    private val limit: Int// Inject the FakeCatApi instead of FakeCatRepository
) : PagingSource<Int, CatResponse>() {

    var errorNextLoad: Boolean = false

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatResponse> {
        val page = params.key ?: 1
        return try {
            // Fetch data from the FakeCatApi
            val data = api.getCats(limit, page = page)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (data.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            errorNextLoad = true
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}