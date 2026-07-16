package com.example.fithub.repositories

import com.example.fithub.roomDB.dao.WeightHistoryDao
import com.example.fithub.roomDB.entities.WeightHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeightHistoryRepositoryImpl @Inject constructor(
    private val weightHistoryDao: WeightHistoryDao
): WeightHistoryRepository {

    override suspend fun addWeight(weightHistory: WeightHistoryEntity): Long {
        return weightHistoryDao.insert(weightHistory)
    }

    override fun getWeightsHistory(): Flow<List<WeightHistoryEntity>> {
        return weightHistoryDao.getAll()
    }
}