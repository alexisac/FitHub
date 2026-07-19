package com.example.fithub.roomDB.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fithub.models.DayType

@Entity(
    tableName = "workout_split_days",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSplitDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutSplitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["workoutSplitId"])
    ]
)
data class WorkoutSplitDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutSplitId: Long,
    val name: String,
    val day: DayType,
    val position: Int
)