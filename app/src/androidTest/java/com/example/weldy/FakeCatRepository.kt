package com.example.weldy

import androidx.paging.PagingSource
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.repository.CatRepository

class FakeCatRepository : CatRepository {

    private val fakeCatResponses = List(100) { index -> CatResponse("Cat$index") }

    override suspend fun getCatsRemote(limit: Int, page: Int): List<CatResponse> {
        val start = (page - 1) * limit
        return fakeCatResponses.subList(start, start + limit)
    }

    override fun getCatsLocal(): PagingSource<Int, CatEntity> {
        throw NotImplementedError("getCatsLocal is not used in this test")
    }

    override suspend fun insertCatToFavourite(catEntity: CatEntity) {
        // No-op for this test
    }
}