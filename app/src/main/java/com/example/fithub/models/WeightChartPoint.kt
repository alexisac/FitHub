package com.example.fithub.models

import java.time.LocalDate

data class WeightChartPoint(
    val date: LocalDate,
    val weight: Double?
)