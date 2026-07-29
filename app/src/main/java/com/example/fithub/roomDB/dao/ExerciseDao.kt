package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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
}