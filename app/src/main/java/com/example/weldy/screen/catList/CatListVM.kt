package com.example.weldy.screen.catList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.repo.CatRepository
import com.example.weldy.repo.paged.CatRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatListVM @Inject constructor(private val catRepository: CatRepository): ViewModel() {

    val cats: Flow<PagingData<CatResponse>> = Pager(PagingConfig(pageSize = 10)) {
        CatRemoteSource(catRepository)
    }.flow.cachedIn(viewModelScope)
}