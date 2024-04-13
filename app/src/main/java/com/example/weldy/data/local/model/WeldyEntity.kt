package com.example.weldy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CatImage")
data class WeldyEntity(@ColumnInfo("id") var id: String? = null,
                       @ColumnInfo("url") var url: String? = null,
                       @ColumnInfo("width") var width: Int? = null,
                       @ColumnInfo("height") var height: Int? = null)
