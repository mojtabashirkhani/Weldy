package com.example.weldy.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weldy.data.local.model.WeldyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeldyDao {
    @Query("SELECT * FROM CatImage")
    fun getCatImages(): Flow<List<WeldyEntity>>

    /**
     *@author Burhan ud din ---> method used to add item searched
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchItem(weldyEntity: WeldyEntity)
}