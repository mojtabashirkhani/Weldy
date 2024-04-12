package com.example.weldy.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weldy.api.Resource
import com.example.weldy.extensions.transform


class GeneralPagingSource<V : Any>(
    private val initialPageIndex: Int = 0,
    private val dataProvider: suspend (pageIndex: Int, loadSize: Int) -> Resource<List<V>?>
) :
    PagingSource<Int, V>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val pageIndex = params.key ?: initialPageIndex
        return dataProvider(pageIndex, params.loadSize).transform {

            if (it.isSuccess()) {
                LoadResult.Page(
                    it.data ?: listOf(),
                    if (pageIndex > initialPageIndex) pageIndex - 1 else null,
                    if (it.data?.isEmpty() == true || (it.data?.size ?: 0) < params.loadSize) null else pageIndex + 1
                )
            } else if (it.isError()) {
                LoadResult.Error(Throwable(it.errorObject?.message))
            } else
                throw IllegalStateException("Unsupported resource type.")
        }
    }

    fun toPager(
        pageSize: Int,
        initialLoadSize: Int = pageSize,
        prefetchDistance: Int = 1,
        enablePlaceholders: Boolean = true
    ) = Pager(
        PagingConfig(
            pageSize = pageSize,
            initialLoadSize = initialLoadSize,
            prefetchDistance = prefetchDistance,
            enablePlaceholders = enablePlaceholders
        ),
    ) { this }

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}




