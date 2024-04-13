package com.example.weldy.repo

import com.example.weldy.api.WeldyApi
import javax.inject.Inject

class CatRepository @Inject constructor(private val weldyApi: WeldyApi) {
    suspend fun getCats(limit: Int, page: Int) =
        weldyApi.getCats(limit, page)
}