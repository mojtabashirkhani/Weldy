package com.example.weldy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weldy.core.DB_VERSION
import com.example.weldy.data.local.dao.WeldyDao
import com.example.weldy.data.local.model.WeldyEntity

@Database(
    entities = [WeldyEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun weldyDao(): WeldyDao
}