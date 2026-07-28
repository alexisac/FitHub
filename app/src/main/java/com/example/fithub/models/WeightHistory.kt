package com.example.fithub.models

import java.time.LocalDateTime

data class WeightHistory(
    val id: Long,
    val dateTime: LocalDateTime,
    val weight: Double
)