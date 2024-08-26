package com.example.weldy.screen.catList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.usecase.GetRemotePaginatedCatsUseCase
import com.example.weldy.domain.repository.paged.CatRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatListVM @Inject constructor(private val getRemotePaginatedCatsUseCase: GetRemotePaginatedCatsUseCase): ViewModel() {

    val cats: Flow<PagingData<Cat>> = Pager(PagingConfig(pageSize = 10)) {
        CatRemoteSource(getRemotePaginatedCatsUseCase)
    }.flow.cachedIn(viewModelScope)
}