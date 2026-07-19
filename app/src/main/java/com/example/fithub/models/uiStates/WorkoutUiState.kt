package com.example.fithub.models.uiStates

import com.example.fithub.models.WorkoutSplitDay

data class WorkoutUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val splitDaysList: List<WorkoutSplitDay> = emptyList()
)