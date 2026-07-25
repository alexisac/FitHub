package com.example.fithub.models.mappers

import com.example.fithub.models.WorkoutSplit
import com.example.fithub.roomDB.entities.WorkoutSplitEntity

fun WorkoutSplitEntity.toModel(): WorkoutSplit = WorkoutSplit(
        id = id,
        name = name,
        startDate = startDate,
        active = active
    )

fun WorkoutSplit.toEntity(): WorkoutSplitEntity = WorkoutSplitEntity(
        id = id,
        name = name,
        startDate = startDate,
        active = active
    )