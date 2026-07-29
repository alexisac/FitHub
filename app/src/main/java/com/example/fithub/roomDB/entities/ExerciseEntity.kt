package com.example.fithub.roomDB.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fithub.models.MuscleGroup

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val muscleGroup: MuscleGroup,
    val description: String
)