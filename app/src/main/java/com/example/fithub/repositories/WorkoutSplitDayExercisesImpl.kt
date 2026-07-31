package com.example.fithub.repositories

import com.example.fithub.models.WorkoutSplitDayExercises
import com.example.fithub.models.mappers.toEntity
import com.example.fithub.roomDB.dao.WorkoutSplitDayExercisesDao
import javax.inject.Inject

class WorkoutSplitDayExercisesImpl @Inject constructor(
    private val workoutSplitDayExerciseDao: WorkoutSplitDayExercisesDao
): WorkoutSplitDayExercisesRepository {
    override suspend fun deleteExercisesFromSplitDay(workoutSplitDayId: Long) =
        workoutSplitDayExerciseDao.deleteExercisesFromSplitDay(workoutSplitDayId)

    override suspend fun insertSplitDayExercise(entity: WorkoutSplitDayExercises) =
        workoutSplitDayExerciseDao.insertSplitDayExercise(entity.toEntity())

    override suspend fun getExerciseIdsForSplitDay(workoutSplitDayId: Long): List<Long> =
        workoutSplitDayExerciseDao.getExerciseIdsForSplitDay(workoutSplitDayId)
}