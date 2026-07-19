package com.example.fithub.repositories

import androidx.room.withTransaction
import com.example.fithub.roomDB.FitHubDatabase
import com.example.fithub.roomDB.dao.WorkoutDao
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val database: FitHubDatabase,
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    override suspend fun createSplit(
        split: WorkoutSplitEntity,
        days: List<WorkoutSplitDayEntity>
    ): Long {
        return database.withTransaction {
            val splitId = workoutDao.addSplit(split)

            val daysWithSplitId = days.map { day ->
                day.copy(
                    workoutSplitId = splitId
                )
            }

            workoutDao.addSplitDays(daysWithSplitId)

            splitId
        }
    }

    override fun getAllSplits(): Flow<List<WorkoutSplitEntity>> =
        workoutDao.getAllSplits()

    override fun getDaysForSplit(
        splitId: Long
    ): Flow<List<WorkoutSplitDayEntity>> =
        workoutDao.getDaysForSplit(splitId)
}