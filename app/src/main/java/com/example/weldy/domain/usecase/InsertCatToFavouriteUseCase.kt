package com.example.weldy.domain.usecase

import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.repositoryImpl.CatRepositoryImpl
import com.example.weldy.domain.model.Cat
import javax.inject.Inject

class InsertCatToFavouriteUseCase @Inject constructor(
    private val catRepository: CatRepositoryImpl
) {
    suspend operator fun invoke(catEntity: Cat) {
        catRepository.insertCatToFavourite(catEntity)
    }
}