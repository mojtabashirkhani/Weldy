package com.example.weldy

import com.example.weldy.data.remote.api.CatApi
import com.example.weldy.data.remote.model.CatResponse

class FakeCatApi: CatApi {

    private val catResponses = mutableListOf<CatResponse>()
    private var shouldReturnError = false

    fun addCatResponse(cat: CatResponse) {
        catResponses.add(cat)
    }

    fun setReturnsError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getCats(limit: Int, page: Int): List<CatResponse> {
        val startIndex = page * limit
        if (shouldReturnError) {
            throw Exception("Simulated API error")
        } else
        return catResponses.subList(startIndex, startIndex + limit)
    }
}