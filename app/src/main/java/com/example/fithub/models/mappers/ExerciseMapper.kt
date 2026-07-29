package com.example.fithub.models.mappers

import com.example.fithub.models.Exercise
import com.example.fithub.roomDB.entities.ExerciseEntity

fun ExerciseEntity.toModel(): Exercise = Exercise(
    id = id,
    name = name,
    muscleGroup = muscleGroup,
    description = description
)

fun Exercise.toEntity(): ExerciseEntity = ExerciseEntity(
    id = id,
    name = name,
    muscleGroup = muscleGroup,
    description = description
)