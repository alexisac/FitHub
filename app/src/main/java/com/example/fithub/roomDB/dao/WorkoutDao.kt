package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSplit(
        split: WorkoutSplitEntity
    ): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSplitDays(
        days: List<WorkoutSplitDayEntity>
    )

    @Query(
        """
        SELECT * 
        FROM workout_splits
        ORDER BY startDate DESC
        """
    )
    fun getAllSplits(): Flow<List<WorkoutSplitEntity>>

    @Query(
        """
        SELECT *
        FROM workout_split_days
        WHERE workoutSplitId = :splitId
        ORDER BY position ASC
        """
    )
    fun getDaysForSplit(
        splitId: Long
    ): Flow<List<WorkoutSplitDayEntity>>
}