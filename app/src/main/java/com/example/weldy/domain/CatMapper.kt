package com.example.weldy.domain

import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.model.Cat

class CatMapper {

    // Maps CatEntity to the domain model Cat
    fun mapEntityToDomain(catEntity: CatEntity): Cat {
        return Cat(
            id = catEntity.id,
            url = catEntity.url,
            width = catEntity.width,
            height = catEntity.height
        )
    }

    // Maps Cat to CatEntity for saving into the database
    fun mapDomainToEntity(cat: Cat): CatEntity {
        return CatEntity(
            id = cat.id,
            url = cat.url,
            width = cat.width,
            height = cat.height
        )
    }

    // Maps CatResponse to the domain model Cat
    fun mapResponseToDomain(catResponse: CatResponse): Cat {
        return Cat(
            id = catResponse.id ?: "-1",
            url = catResponse.url,
            width = catResponse.width,
            height = catResponse.height
        )
    }

    // Maps a list of CatEntity to a list of Cat
    fun mapEntityListToDomainList(catEntities: List<CatEntity>): List<Cat> {
        return catEntities.map { mapEntityToDomain(it) }
    }

    // Maps a list of CatResponse to a list of Cat
    fun mapResponseListToDomainList(catResponses: List<CatResponse>): List<Cat> {
        return catResponses.map { mapResponseToDomain(it) }
    }
}