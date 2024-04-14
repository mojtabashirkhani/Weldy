package com.example.weldy.repo.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.weldy.data.local.model.WeldyEntity
import com.example.weldy.repo.CatRepository
import javax.inject.Inject

class CatLocalSource @Inject constructor(private val catRepository: CatRepository): PagingSource<Int, WeldyEntity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WeldyEntity> {
        return try {
            val nextPage = params.key ?: 1
            val catListResponse = catRepository.getCatsLocal()

            LoadResult.Page(
                data = catListResponse,
                prevKey =  null,
                nextKey =  null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, WeldyEntity>): Int? {
        return state.anchorPosition
    }

}