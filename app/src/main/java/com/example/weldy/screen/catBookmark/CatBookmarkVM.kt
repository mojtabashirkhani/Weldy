package com.example.weldy.screen.catBookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weldy.data.local.model.WeldyEntity
import com.example.weldy.repo.CatRepository
import com.example.weldy.repo.paged.CatLocalSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CatBookmarkVM @Inject constructor(private val catRepository: CatRepository): ViewModel() {

    val cats: Flow<PagingData<WeldyEntity>> = Pager(PagingConfig(pageSize = 10)) {
        CatLocalSource(catRepository)
    }.flow.cachedIn(viewModelScope)
}