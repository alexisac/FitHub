package com.example.fithub.models.uiStates

import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay
import java.time.LocalDate

data class WorkoutUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    val splitName: String = "",
    val selectedSplitDate: String = "",
    val splitDaysList: List<WorkoutSplitDay> = emptyList(),

    val splits: List<WorkoutSplit> = emptyList(),
    val startDate: LocalDate = LocalDate.now(),
    val isActive: Boolean = false
)