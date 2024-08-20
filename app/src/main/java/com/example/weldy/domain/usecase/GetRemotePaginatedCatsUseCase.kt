package com.example.weldy.domain.usecase

import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import javax.inject.Inject

class GetRemotePaginatedCatsUseCase @Inject constructor(
    private val catRepository: CatRepositoryImpl
) {
    suspend operator fun invoke(limit: Int, page: Int): List<CatResponse> {
        return catRepository.getCatsRemote(limit, page)
    }
}