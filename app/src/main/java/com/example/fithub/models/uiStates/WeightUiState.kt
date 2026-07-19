package com.example.fithub.models.uiStates

data class WeightUiState (
    val message: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)