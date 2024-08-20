package com.example.weldy.data.repositoryImpl

import androidx.paging.PagingSource
import com.example.weldy.data.local.dao.CatDao
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.api.CatApi
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(private val catApi: CatApi, private val catDao: CatDao):
    CatRepository {

    override suspend fun getCatsRemote(limit: Int, page: Int): List<CatResponse> {
        return catApi.getCats(limit, page)
    }

    override fun getCatsLocal(): PagingSource<Int, CatEntity> {
        return catDao.getCatImages()
    }

    override suspend fun insertCatToFavourite(catEntity: CatEntity) {
        catDao.insertCatImageToFavourite(catEntity)
    }

}