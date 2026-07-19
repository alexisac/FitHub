package com.example.fithub.models

data class WorkoutSplitDay(
    val id: Long,
    val name: String,
    val day: DayType,
    val position: Int
)