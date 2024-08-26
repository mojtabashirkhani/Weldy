package com.example.weldy.domain.usecase

import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.model.Cat
import javax.inject.Inject

class GetRemotePaginatedCatsUseCase @Inject constructor(
    private val catRepository: CatRepositoryImpl
) {
    suspend operator fun invoke(limit: Int, page: Int): List<Cat> {
        return catRepository.getCatsRemote(limit, page)
    }
}