package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.models.uiStates.WorkoutUiState
import com.example.fithub.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutService: WorkoutService
): ViewModel() {
    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    fun addWorkoutDay(
        name: String,
        dayType: DayType
    ) {
        if (name.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Day name cannot be empty.")
            }
            return
        }

        _uiState.update { currentState ->
            val newDay = WorkoutSplitDay(
                // temporary ID. Needs in reordable list
                id = System.currentTimeMillis(),
                name = name.trim(),
                day = dayType,
                position = currentState.splitDaysList.size + 1
            )

            currentState.copy(
                splitDaysList = currentState.splitDaysList + newDay,
                errorMessage = null,
                successMessage = "Add successfully"
            )
        }
    }

    fun moveWorkoutDay(
        fromIndex: Int,
        toIndex: Int
    ) {
        _uiState.update { currentState ->
            val updatedList = currentState.splitDaysList.toMutableList()

            val movedDay = updatedList.removeAt(fromIndex)
            updatedList.add(toIndex, movedDay)

            val listWithUpdatedPosition = updatedList.mapIndexed { index, day ->
                day.copy(position = index + 1)
            }

            currentState.copy(
                splitDaysList = listWithUpdatedPosition
            )
        }
    }

    fun createSplit(
        splitName: String,
        selectedDate: String
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
                workoutService.createSplit(
                    splitName = splitName,
                    selectedDate = selectedDate,
                    splitDaysList = uiState.value.splitDaysList
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        successMessage = "Workout added successfully\n"
                    )
                }
            } catch (ex: ValidationException) {
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

    fun clearMessages() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }
}