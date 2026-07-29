package com.example.fithub.repositories

import com.example.fithub.models.Exercise
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
}