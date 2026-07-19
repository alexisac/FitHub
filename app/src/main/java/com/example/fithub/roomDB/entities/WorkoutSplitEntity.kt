package com.example.fithub.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "workout_splits")
data class WorkoutSplitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val startDate: LocalDate,
    val active: Boolean = false
)