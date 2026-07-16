package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fithub.roomDB.entities.WeightHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightHistoryDao {
    @Insert
    suspend fun insert(weightHistory: WeightHistoryEntity): Long

    @Query("SELECT * FROM weight_history ORDER BY dateTime DESC")
    fun getAll(): Flow<List<WeightHistoryEntity>>
}