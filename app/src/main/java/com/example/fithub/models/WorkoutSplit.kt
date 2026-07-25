package com.example.fithub.models

import java.time.LocalDate

data class WorkoutSplit(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val active: Boolean
)