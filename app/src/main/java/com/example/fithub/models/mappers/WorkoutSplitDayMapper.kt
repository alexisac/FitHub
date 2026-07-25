package com.example.fithub.models.mappers

import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity

fun WorkoutSplitDayEntity.toModel(): WorkoutSplitDay = WorkoutSplitDay(
    id = id,
    workoutSplitId = workoutSplitId,
    name = name,
    day = day,
    position = position
)

fun WorkoutSplitDay.toEntity(): WorkoutSplitDayEntity = WorkoutSplitDayEntity(
    id = id,
    workoutSplitId = workoutSplitId,
    name = name,
    day = day,
    position = position
)
