package com.example.fithub.models.uiStates

import com.example.fithub.models.Exercise

data class ExerciseUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    val exercisesList: List<Exercise> = emptyList(),
    val selectedExercise: Exercise? = null
)