package com.example.weldy

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weldy.domain.model.Cat

class FakeCatPagingSource (private val repository: FakeCatRepository) : PagingSource<Int, Cat>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val page = params.key ?: 1
        val data = repository.getCatsRemote(params.loadSize, page)
        val prevKey = if (page > 1) page - 1 else null
        val nextKey = if (data.isNotEmpty()) page + 1 else null

        return LoadResult.Page(
            data = data,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}