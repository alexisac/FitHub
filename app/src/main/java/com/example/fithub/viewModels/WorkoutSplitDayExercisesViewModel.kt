package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.messages.ViewModelErrorMessages
import com.example.fithub.common.messages.ViewModelSuccessMessages
import com.example.fithub.models.uiStates.WorkoutSplitDayExercisesUiState
import com.example.fithub.services.WorkoutSplitDayExercisesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutSplitDayExercisesViewModel @Inject constructor(
    private val workoutSplitDayExercisesService: WorkoutSplitDayExercisesService
): ViewModel() {
    private val _uiState = MutableStateFlow(WorkoutSplitDayExercisesUiState())
    val uiState: StateFlow<WorkoutSplitDayExercisesUiState> = _uiState.asStateFlow()

    fun saveExercisesForSplitDay(
        workoutSplitDayId: Long,
        exerciseIds: List<Long>
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
                workoutSplitDayExercisesService.saveExercisesForSplitDay(
                    workoutSplitDayId = workoutSplitDayId,
                    exerciseIds = exerciseIds
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        successMessage = ViewModelSuccessMessages.EXERCISE_ASSIGNED_SUCCESSFULLY
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

    fun getSelectedExercises(
        workoutSplitDayId: Long
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val selectedIds = workoutSplitDayExercisesService.getExerciseIdsForSplitDay(workoutSplitDayId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        selectedExerciseIds = selectedIds.toSet()
                    )
                }
            } catch (ex: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = ex.message ?: ViewModelErrorMessages.UNKNOWN_ERROR
                    )
                }
            }
        }
    }

    fun toggleExerciseSelection(
        exerciseId: Long
    ) {
        _uiState.update { currentState ->
            val updatedIds =
                if (exerciseId in currentState.selectedExerciseIds) {
                    currentState.selectedExerciseIds - exerciseId
                } else {
                    currentState.selectedExerciseIds + exerciseId
                }

            currentState.copy(
                selectedExerciseIds = updatedIds
            )
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                successMessage = null,
                selectedExerciseIds = emptySet()
            )
        }
    }
}