package com.example.fithub.repositories

import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun createSplit(
        split: WorkoutSplitEntity,
        days: List<WorkoutSplitDayEntity>
    ): Long

    fun getAllSplits(): Flow<List<WorkoutSplitEntity>>

    fun getDaysForSplit(
        splitId: Long
    ): Flow<List<WorkoutSplitDayEntity>>
}