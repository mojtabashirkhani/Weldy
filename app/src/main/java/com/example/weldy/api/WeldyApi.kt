package com.example.weldy.api

import com.example.weldy.data.CatResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeldyApi {
    @GET("v1/images/search")
    suspend fun getCats(
        @Query("limit") limit: Int = 1,
        @Query("page") page: Int = 0
    ): List<CatResponse>
}