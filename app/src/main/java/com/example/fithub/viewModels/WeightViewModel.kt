package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.models.uiStates.WeightUiState
import com.example.fithub.services.WeightHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val weightHistoryService: WeightHistoryService
): ViewModel(){
    private val _uiState = MutableStateFlow(WeightUiState())
    val uiState: StateFlow<WeightUiState> = _uiState.asStateFlow()

    fun addWeight(
        weight: String,
        selectedDate: String,
        selectedTime: String
    ){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {
                weightHistoryService.addWeight(
                    weight = weight,
                    date = selectedDate,
                    time = selectedTime
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        successMessage = "Weight added successfully\n"
                    )
                }
            } catch (ex: ValidationException){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: "Invalid information\n",
                        successMessage = null
                    )
                }
            } catch (ex: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: "Unknown error\n",
                        successMessage = null
                    )
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun testDB(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                weightHistoryService.getWeightHistory().collect { weightHistory ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = buildString {
                                appendLine("Number of records: ${weightHistory.size}")
                                appendLine()
                                weightHistory.forEach { record ->
                                    appendLine("${record.id} - ${record.weight} kg - ${record.dateTime}")
                                }
                            }
                        )
                    }
                }
            } catch (ex: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}