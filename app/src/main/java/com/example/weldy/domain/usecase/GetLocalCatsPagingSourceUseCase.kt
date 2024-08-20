package com.example.weldy.domain.usecase

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import javax.inject.Inject

class GetLocalCatsPagingSourceUseCase @Inject constructor(
    private val catRepository: CatRepositoryImpl
) {
    operator fun invoke(): PagingSource<Int, CatEntity> {
        return catRepository.getCatsLocal()
    }
}