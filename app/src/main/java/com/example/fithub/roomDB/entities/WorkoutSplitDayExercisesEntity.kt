package com.example.fithub.roomDB.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_split_day_exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSplitDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutSplitDayId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["workoutSplitDayId"]),
        Index(value = ["exerciseId"]),
        Index(
            value = ["workoutSplitDayId", "exerciseId"],
            unique = true
        )
    ]
)
data class WorkoutSplitDayExercisesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutSplitDayId: Long,
    val exerciseId: Long
)