package com.example.weldy.data.repositoryImpl

import androidx.paging.PagingSource
import com.example.weldy.data.local.dao.CatDao
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.api.CatApi
import com.example.weldy.data.mapper.CatMapper
import com.example.weldy.domain.model.Cat
import com.example.weldy.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(private val catApi: CatApi, private val catDao: CatDao, private val catMapper: CatMapper):
    CatRepository {

    // Fetches cats from the remote API and maps the response to domain model
    override suspend fun getCatsRemote(limit: Int, page: Int): List<Cat> {
        val catResponses = catApi.getCats(limit, page)
        return catMapper.mapResponseListToDomainList(catResponses)
    }

    // Fetches cats from the local database as a PagingSource
    // You can return the PagingSource directly, or if you need to map the data before returning,
    // you'd need a different approach since PagingSource doesn't allow modification of data in the middle.
    override fun getCatsLocal(): PagingSource<Int, CatEntity> {
        return catDao.getCatImages()
    }

    // Inserts a cat to the local database as a favourite
    override suspend fun insertCatToFavourite(cat: Cat) {
        val catEntity = catMapper.mapDomainToEntity(cat)
        catDao.insertCatImageToFavourite(catEntity)
    }

}