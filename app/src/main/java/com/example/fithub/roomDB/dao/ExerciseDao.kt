package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fithub.models.MuscleGroup
import com.example.fithub.roomDB.entities.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert
    suspend fun addExercise(exerciseEntity: ExerciseEntity): Long

    @Query("""
        SELECT *
        FROM exercises
    """)
    suspend fun getAll(): List<ExerciseEntity>

    @Query("""
        DELETE FROM exercises
        WHERE id = :exerciseId
    """)
    suspend fun deleteExercise(exerciseId: Long)

    @Query("""
        SELECT *
        FROM exercises
        WHERE id = :exerciseId
    """)
    suspend fun getExerciseById(exerciseId: Long): ExerciseEntity

    @Query("""
        UPDATE exercises
        SET name = :exerciseName,
            muscleGroup = :muscleGroup,
            description = :exerciseDescription
        WHERE id = :exerciseId
    """)
    suspend fun updateExercise(
        exerciseId: Long,
        exerciseName: String,
        muscleGroup: MuscleGroup,
        exerciseDescription: String
    )
}