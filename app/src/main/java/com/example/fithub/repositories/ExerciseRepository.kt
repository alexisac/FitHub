package com.example.fithub.repositories

import com.example.fithub.models.Exercise
import com.example.fithub.models.MuscleGroup

interface ExerciseRepository {
    suspend fun addExercise(exercise: Exercise): Long

    suspend fun getAll(): List<Exercise>

    suspend fun deleteExercise(exerciseId: Long)

    suspend fun getExerciseById(exerciseId: Long): Exercise

    suspend fun updateExercise(
        exerciseId: Long,
        exerciseName: String,
        muscleGroup: MuscleGroup,
        exerciseDescription: String
    )
}