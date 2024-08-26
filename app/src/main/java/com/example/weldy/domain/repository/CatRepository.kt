package com.example.weldy.domain.repository

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.model.Cat


interface  CatRepository {
    suspend fun getCatsRemote(limit: Int, page: Int): List<Cat>
    fun getCatsLocal(): PagingSource<Int, CatEntity>
    suspend fun insertCatToFavourite(catEntity: Cat)
}