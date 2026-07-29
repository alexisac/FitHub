package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ViewModelErrorMessages
import com.example.fithub.common.messages.ViewModelSuccessMessages
import com.example.fithub.models.MuscleGroup
import com.example.fithub.models.uiStates.ExerciseUiState
import com.example.fithub.services.ExerciseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService
): ViewModel() {
    private val _uiState = MutableStateFlow(ExerciseUiState())
    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()

    fun addExercise(
        exerciseName: String,
        muscleGroup: MuscleGroup,
        exerciseDescription: String
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {
                exerciseService.addExercise(
                    name = exerciseName,
                    muscleGroup = muscleGroup,
                    description = exerciseDescription
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        successMessage = ViewModelSuccessMessages.EXERCISE_ADDED_SUCCESSFULLY
                    )
                }
            } catch (ex: ValidationException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: ViewModelErrorMessages.INVALID_INFORMATION,
                        successMessage = null
                    )
                }
            } catch (ex: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: ViewModelErrorMessages.UNKNOWN_ERROR,
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
}