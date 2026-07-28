package com.example.fithub.repositories

import com.example.fithub.models.WeightHistory
import com.example.fithub.models.mappers.toEntity
import com.example.fithub.models.mappers.toModel
import com.example.fithub.roomDB.dao.WeightHistoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightHistoryRepositoryImpl @Inject constructor(
    private val weightHistoryDao: WeightHistoryDao
): WeightHistoryRepository {

    override suspend fun addWeight(weightHistory: WeightHistory): Long {
        return weightHistoryDao.insert(weightHistory.toEntity())
    }

    override fun getWeightsHistory(): Flow<List<WeightHistory>> {
        return weightHistoryDao.getAll()
            .map { entities ->
                entities.map { entity ->
                    entity.toModel()
                }
            }
    }
}