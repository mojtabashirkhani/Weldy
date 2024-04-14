package com.example.weldy.repo

import com.example.weldy.data.local.dao.WeldyDao
import com.example.weldy.data.local.model.WeldyEntity
import com.example.weldy.data.remote.api.WeldyApi
import javax.inject.Inject

class CatRepository @Inject constructor(private val weldyApi: WeldyApi, private val weldyDao: WeldyDao) {
    suspend fun getCatsRemote(limit: Int, page: Int) =
        weldyApi.getCats(limit, page)

    suspend fun getCatsLocal() = weldyDao.getCatImages()

    suspend fun insertCatToFavourite(weldyEntity: WeldyEntity) = weldyDao.insertCatImageToFavourite(weldyEntity)
}