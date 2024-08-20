package com.example.weldy.domain.repository

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.model.CatResponse


interface  CatRepository {
    suspend fun getCatsRemote(limit: Int, page: Int): List<CatResponse>
    fun getCatsLocal(): PagingSource<Int, CatEntity>
    suspend fun insertCatToFavourite(catEntity: CatEntity)
}