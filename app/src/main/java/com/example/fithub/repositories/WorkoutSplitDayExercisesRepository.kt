package com.example.fithub.repositories

import com.example.fithub.models.WorkoutSplitDayExercises

interface WorkoutSplitDayExercisesRepository {
    suspend fun deleteExercisesFromSplitDay(
        workoutSplitDayId: Long
    )

    suspend fun insertSplitDayExercise(
        entity: WorkoutSplitDayExercises
    )

    suspend fun getExerciseIdsForSplitDay(
        workoutSplitDayId: Long
    ): List<Long>
}