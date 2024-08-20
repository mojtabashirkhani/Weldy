package com.example.weldy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weldy.DB_VERSION
import com.example.weldy.data.local.dao.CatDao
import com.example.weldy.data.local.model.CatEntity

@Database(
    entities = [CatEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class CatDatabase : RoomDatabase()  {
    abstract fun catDao(): CatDao
}