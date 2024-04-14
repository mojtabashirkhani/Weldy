package com.example.weldy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CatImage")
data class CatEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("url") var url: String? = null,
    @ColumnInfo("width") var width: Int? = null,
    @ColumnInfo("height") var height: Int? = null
)
