package com.example.fithub.models.mappers

import com.example.fithub.models.WorkoutSplitDayExercises
import com.example.fithub.roomDB.entities.WorkoutSplitDayExercisesEntity

fun WorkoutSplitDayExercisesEntity.toModel(): WorkoutSplitDayExercises = WorkoutSplitDayExercises(
    id = id,
    workoutSplitDayId = workoutSplitDayId,
    exerciseId = exerciseId
)

fun WorkoutSplitDayExercises.toEntity(): WorkoutSplitDayExercisesEntity = WorkoutSplitDayExercisesEntity(
    id = id,
    workoutSplitDayId = workoutSplitDayId,
    exerciseId = exerciseId
)