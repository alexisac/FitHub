package com.example.fithub.repositories

import androidx.room.withTransaction
import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.models.mappers.toEntity
import com.example.fithub.models.mappers.toModel
import com.example.fithub.roomDB.FitHubDatabase
import com.example.fithub.roomDB.dao.WorkoutDao
import java.time.LocalDate
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val database: FitHubDatabase,
    private val workoutDao: WorkoutDao
) : WorkoutRepository {

    override suspend fun createSplit(
        split: WorkoutSplit,
        days: List<WorkoutSplitDay>
    ): Long {
        return database.withTransaction {
            val splitId = workoutDao.addSplit(split.toEntity())

            val daysWithSplitId = days.map { day ->
                day.copy(
                    workoutSplitId = splitId
                ).toEntity()
            }

            workoutDao.addSplitDays(daysWithSplitId)

            splitId
        }
    }

    override suspend fun getAllSplits(): List<WorkoutSplit> {
        return workoutDao.getAllSplits()
            .map { entity ->
                entity.toModel()
            }
    }

    override suspend fun getDaysForSplit(
        splitId: Long
    ): List<WorkoutSplitDay> {
        return workoutDao.getDaysForSplit(splitId)
            .map { entity ->
                entity.toModel()
            }
    }

    override suspend fun getSplitById(splitId: Long): WorkoutSplit =
        workoutDao.getSplitById(splitId).toModel()

    override suspend fun getSplitDayById(splitDayId: Long): WorkoutSplitDay =
        workoutDao.getSplitDayById(splitDayId).toModel()

    override suspend fun setActiveSplit(splitId: Long) =
        workoutDao.setActiveSplit(splitId)

    override suspend fun updateStartDate(
        splitId: Long,
        startDate: LocalDate
    ) = workoutDao.updateStartDate(splitId, startDate)

    override suspend fun deleteSplit(splitId: Long) =
        workoutDao.deleteSplit(splitId)
}