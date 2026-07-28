package com.example.fithub.models.mappers

import com.example.fithub.models.WeightHistory
import com.example.fithub.roomDB.entities.WeightHistoryEntity

fun WeightHistoryEntity.toModel(): WeightHistory = WeightHistory(
    id = id,
    dateTime = dateTime,
    weight = weight
)

fun WeightHistory.toEntity(): WeightHistoryEntity = WeightHistoryEntity(
    id = id,
    dateTime = dateTime,
    weight = weight
)