package com.example.weldy.fragment.catList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.weldy.api.Resource
import com.example.weldy.api.WeldyApi
import com.example.weldy.data.CatResponse
import com.example.weldy.repo.CatRepository
import com.example.weldy.repo.paged.CatSource
import com.example.weldy.util.GeneralPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class CatListFragmentVM @Inject constructor(private val catRepository: CatRepository): ViewModel() {

    val user: Flow<PagingData<CatResponse>> = Pager(PagingConfig(pageSize = 100)) {
        CatSource(catRepository)
    }.flow.cachedIn(viewModelScope)
}