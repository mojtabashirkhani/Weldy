package com.example.weldy.screen.catBookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.domain.CatMapper
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.usecase.GetLocalCatsPagingSourceUseCase
import com.example.weldy.extensions.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CatBookmarkVM @Inject constructor(private val getLocalCatsPagingSourceUseCase: GetLocalCatsPagingSourceUseCase,  private val catMapper: CatMapper): ViewModel() {

    val cats: Flow<PagingData<Cat>> = Pager(PagingConfig(pageSize = 10)) {
        getLocalCatsPagingSourceUseCase()
    }.flow.map { pagingData ->
        pagingData.map { catEntity ->
            catMapper.mapEntityToDomain(catEntity)
        }
    }.cachedIn(viewModelScope)

}