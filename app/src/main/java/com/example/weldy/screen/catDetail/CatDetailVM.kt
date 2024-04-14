package com.example.weldy.screen.catDetail

import androidx.lifecycle.ViewModel
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.repo.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatDetailVM @Inject constructor(private val catRepository: CatRepository): ViewModel() {

    suspend fun insertCatToFavourite(catEntity: CatEntity) {
        catRepository.insertCatToFavourite(catEntity)
    }

}