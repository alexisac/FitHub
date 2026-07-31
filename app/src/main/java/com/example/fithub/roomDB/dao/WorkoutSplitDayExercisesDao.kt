package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fithub.roomDB.entities.WorkoutSplitDayExercisesEntity

@Dao
interface WorkoutSplitDayExercisesDao {
    @Query("""
        DELETE FROM workout_split_day_exercises
        WHERE workoutSplitDayId = :workoutSplitDayId
    """)
    suspend fun deleteExercisesFromSplitDay(
        workoutSplitDayId: Long
    )

    @Insert
    suspend fun insertSplitDayExercise(
        entity: WorkoutSplitDayExercisesEntity
    )
}