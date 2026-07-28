package com.example.fithub.repositories

import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay
import java.time.LocalDate

interface WorkoutRepository {
    suspend fun createSplit(
        split: WorkoutSplit,
        days: List<WorkoutSplitDay>
    ): Long

    suspend fun getAllSplits(): List<WorkoutSplit>

    suspend fun getDaysForSplit(
        splitId: Long
    ): List<WorkoutSplitDay>

    suspend fun getSplitById(
        splitId: Long
    ): WorkoutSplit

    suspend fun setActiveSplit(
        splitId: Long
    )

    suspend fun updateStartDate(
        splitId: Long,
        startDate: LocalDate
    )
}