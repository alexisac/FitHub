package com.example.fithub.repositories

import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun createSplit(
        split: WorkoutSplit,
        days: List<WorkoutSplitDay>
    ): Long

    fun getAllSplits(): Flow<List<WorkoutSplit>>

    fun getDaysForSplit(
        splitId: Long
    ): Flow<List<WorkoutSplitDay>>
}