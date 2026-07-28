package com.example.fithub.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity
import java.time.LocalDate

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
    suspend fun getAllSplits(): List<WorkoutSplitEntity>

    @Query(
        """
        SELECT *
        FROM workout_split_days
        WHERE workoutSplitId = :splitId
        ORDER BY position ASC
        """
    )
    suspend fun getDaysForSplit(
        splitId: Long
    ): List<WorkoutSplitDayEntity>

    @Query("""
        SELECT *
        FROM workout_splits
        WHERE id = :splitId
    """)
    suspend fun getSplitById(
        splitId: Long
    ): WorkoutSplitEntity

    @Query("""
        UPDATE workout_splits
        SET active = CASE
            WHEN id = :splitId THEN 1
            ELSE 0
        END
    """)
    suspend fun setActiveSplit(
        splitId: Long
    )

    @Query("""
        UPDATE workout_splits
        SET startDate = :startDate
        WHERE id = :splitId
    """)
    suspend fun updateStartDate(
        splitId: Long,
        startDate: LocalDate
    )
}