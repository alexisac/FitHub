package com.example.fithub.repositories

import com.example.fithub.roomDB.entities.WeightHistoryEntity
import kotlinx.coroutines.flow.Flow

interface WeightHistoryRepository {

    suspend fun addWeight(weightHistory: WeightHistoryEntity): Long
    fun getWeightsHistory(): Flow<List<WeightHistoryEntity>>
}