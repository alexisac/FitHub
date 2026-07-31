package com.example.fithub.services

import com.example.fithub.models.WorkoutSplitDayExercises
import com.example.fithub.repositories.WorkoutSplitDayExercisesRepository
import javax.inject.Inject

class WorkoutSplitDayExercisesService @Inject constructor(
    private val workoutSplitDayExercisesRepository: WorkoutSplitDayExercisesRepository
){
    suspend fun saveExercisesForSplitDay(
        workoutSplitDayId: Long,
        exerciseIds: List<Long>
    ) {
        workoutSplitDayExercisesRepository.deleteExercisesFromSplitDay(workoutSplitDayId)

        exerciseIds.forEach { exerciseId ->
            val workoutSplitDayExercises = WorkoutSplitDayExercises(
                id = 0,
                workoutSplitDayId = workoutSplitDayId,
                exerciseId = exerciseId
            )
            workoutSplitDayExercisesRepository.insertSplitDayExercise(workoutSplitDayExercises)
        }
    }

    suspend fun getExerciseIdsForSplitDay(workoutSplitDayId: Long): List<Long> =
        workoutSplitDayExercisesRepository.getExerciseIdsForSplitDay(workoutSplitDayId)
}