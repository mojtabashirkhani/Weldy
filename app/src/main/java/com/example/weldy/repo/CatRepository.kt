package com.example.weldy.repo

import com.example.weldy.data.local.dao.CatDao
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.api.CatApi
import javax.inject.Inject

class CatRepository @Inject constructor(private val catApi: CatApi, private val catDao: CatDao) {
    suspend fun getCatsRemote(limit: Int, page: Int) =
        catApi.getCats(limit, page)

    suspend fun getCatsLocal() = catDao.getCatImages()

    suspend fun insertCatToFavourite(catEntity: CatEntity) = catDao.insertCatImageToFavourite(catEntity)
}