package com.example.weldy.domain.repository.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import javax.inject.Inject

class CatRemoteSource @Inject constructor(private val getRemotePaginatedCatsUseCase: GetRemotePaginatedCatsUseCase): PagingSource<Int, CatResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatResponse> {
        return try {
            val nextPage = params.key ?: 1
            val catListResponse = getRemotePaginatedCatsUseCase(params.loadSize, nextPage)

            LoadResult.Page(
                data = catListResponse,
                prevKey = if (nextPage > 0) nextPage - 1 else null,
                nextKey = if (catListResponse.isEmpty() || catListResponse.size < params.loadSize) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatResponse>): Int? {
        return state.anchorPosition
    }

}