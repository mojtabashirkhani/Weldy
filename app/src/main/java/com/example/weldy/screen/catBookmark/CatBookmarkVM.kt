package com.example.weldy.screen.catBookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.domain.usecase.GetLocalCatsPagingSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatBookmarkVM @Inject constructor(private val getLocalCatsPagingSourceUseCase: GetLocalCatsPagingSourceUseCase): ViewModel() {

    val cats: Flow<PagingData<CatEntity>> = Pager(PagingConfig(pageSize = 10)) {
        getLocalCatsPagingSourceUseCase()
    }.flow.cachedIn(viewModelScope)

}