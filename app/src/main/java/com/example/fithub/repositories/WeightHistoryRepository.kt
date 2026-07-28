package com.example.fithub.repositories

import com.example.fithub.models.WeightHistory
import kotlinx.coroutines.flow.Flow

interface WeightHistoryRepository {

    suspend fun addWeight(weightHistory: WeightHistory): Long
    fun getWeightsHistory(): Flow<List<WeightHistory>>
}