package com.example.fithub.models.uiStates

import com.example.fithub.models.WeightChartPoint

data class WeightUiState (
    val message: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    val weightChart: List<WeightChartPoint> = emptyList()
)