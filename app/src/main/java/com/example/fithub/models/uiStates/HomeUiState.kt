package com.example.fithub.models.uiStates

data class HomeUiState (
    val message: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)