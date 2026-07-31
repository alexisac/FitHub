package com.example.fithub.repositories

import com.example.fithub.models.Exercise
import com.example.fithub.models.MuscleGroup
import com.example.fithub.models.mappers.toEntity
import com.example.fithub.models.mappers.toModel
import com.example.fithub.roomDB.dao.ExerciseDao
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
): ExerciseRepository {
    override suspend fun addExercise(exercise: Exercise): Long =
        exerciseDao.addExercise(exercise.toEntity())

    override suspend fun getAll(): List<Exercise> =
        exerciseDao.getAll()
            .map { entity ->
                entity.toModel()
            }

    override suspend fun deleteExercise(exerciseId: Long) =
        exerciseDao.deleteExercise(exerciseId)

    override suspend fun getExerciseById(exerciseId: Long): Exercise =
        exerciseDao.getExerciseById(exerciseId).toModel()

    override suspend fun updateExercise(
        exerciseId: Long,
        exerciseName: String,
        muscleGroup: MuscleGroup,
        exerciseDescription: String
    ) =
        exerciseDao.updateExercise(
            exerciseId,
            exerciseName,
            muscleGroup,
            exerciseDescription
        )
}