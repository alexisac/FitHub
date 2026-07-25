package com.example.fithub.repositories

import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay

interface WorkoutRepository {
    suspend fun createSplit(
        split: WorkoutSplit,
        days: List<WorkoutSplitDay>
    ): Long

    fun getAllSplits(): List<WorkoutSplit>

    fun getDaysForSplit(
        splitId: Long
    ): List<WorkoutSplitDay>
}