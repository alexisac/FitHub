package com.example.fithub.services

import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ServiceMessages
import com.example.fithub.models.Exercise
import com.example.fithub.models.MuscleGroup
import com.example.fithub.repositories.ExerciseRepository
import javax.inject.Inject

class ExerciseService @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend fun addExercise(
        name: String,
        muscleGroup: MuscleGroup,
        description: String
    ): Long {
        if (name.isBlank()) {
            throw ValidationException(ServiceMessages.EXERCISE_NAME_NOT_EMPTY)
        }

        if (name.trim().length !in 1..100) {
            throw ValidationException(ServiceMessages.EXERCISE_NAME_LIMIT)
        }

        if (description.trim().length > 300) {
            throw ValidationException(ServiceMessages.EXERCISE_DESCRIPTION_LIMIT)
        }

        val exercise = Exercise(
            id = 0,
            name = name.trim(),
            muscleGroup = muscleGroup,
            description = description.trim()
        )

        return exerciseRepository.addExercise(exercise)
    }

    suspend fun getAllExercises(): List<Exercise> =
        exerciseRepository.getAll()
}