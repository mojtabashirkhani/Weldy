package com.example.weldy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weldy.data.local.model.CatEntity

@Dao
interface CatDao {
    @Query("SELECT * FROM catimage")
    suspend fun getCatImages(): List<CatEntity>

    /**
     *@author Burhan ud din ---> method used to add item searched
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImageToFavourite(weldyEntity: CatEntity)
}