package com.example.fithub.repositories

import com.example.fithub.models.Exercise

interface ExerciseRepository {
    suspend fun addExercise(exercise: Exercise): Long

    suspend fun getAll(): List<Exercise>
}