package com.example.fithub.models.uiStates

data class ExerciseUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)