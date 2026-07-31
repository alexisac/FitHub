package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ViewModelErrorMessages
import com.example.fithub.common.messages.ViewModelSuccessMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.uiStates.WorkoutUiState
import com.example.fithub.services.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
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
        try {
            val currentState = uiState.value

            val newDay = workoutService.createWorkoutDay(
                name = name,
                dayType = dayType,
                position = currentState.splitDaysList.size + 1
            )

            _uiState.update {
                it.copy(
                    splitDaysList = it.splitDaysList + newDay,
                    errorMessage = null,
                    successMessage = ViewModelSuccessMessages.DAY_WORKOUT_ADDED_SUCCESSFULLY
                )
            }
        } catch (ex: ValidationException){
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
        splitName: String
    ) {
        val selectedDate = LocalDate.now()

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
                        successMessage = ViewModelSuccessMessages.WORKOUT_ADDED_SUCCESSFULLY
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

    fun updateSplitName(name: String) {
        _uiState.update {
            it.copy(
                splitName = name
            )
        }
    }

    fun deleteSplitDay(index: Int) {
        _uiState.update {
            it.copy(
                splitDaysList = it.splitDaysList.toMutableList()
                    .apply { removeAt(index) }
                    .mapIndexed { i, day -> day.copy(position = i + 1) }
            )
        }
    }

    fun getAllSplits() {
        viewModelScope.launch {
            val splits = workoutService.getAllSplits()
            _uiState.update {
                it.copy(
                    splits = splits
                )
            }
        }
    }

    fun getAllSplitDays(splitId: Long) {
        viewModelScope.launch {
            val days = workoutService.getDaysForSplit(splitId)
            _uiState.update {
                it.copy(
                    splitDaysList = days
                )
            }
        }
    }

    fun getSplitById(splitId: Long) {
        viewModelScope.launch {
            val split = workoutService.getSplitById(splitId)
            _uiState.update {
                it.copy(
                    startDate = split.startDate,
                    isActive = split.active
                )
            }
        }
    }

    fun getSplitDayById(splitDayId: Long) {
        viewModelScope.launch {
            val splitDay = workoutService.getSplitDayById(splitDayId)
            _uiState.update {
                it.copy(
                    splitDay = splitDay
                )
            }
        }
    }

    fun updateSplitDetails(splitId: Long) {
        val startDate = LocalDate.now()

        viewModelScope.launch {
            workoutService.setActiveSplit(splitId)
            workoutService.updateStartDate(splitId, startDate)

            _uiState.update {
                it.copy(
                    startDate = startDate,
                    isActive = true
                )
            }
        }
    }

    fun deleteSplit(splitId: Long) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null
                )
            }

            try {
                workoutService.deleteSplit(splitId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        successMessage = ViewModelSuccessMessages.SPLIT_DELETED_SUCCESSFULLY
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

    fun clearMessages() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun clearSplitDraft() {
        _uiState.update {
            it.copy(
                splitName = "",
                selectedSplitDate = "",
                splitDaysList = emptyList()
            )
        }
    }

    fun clearSplitDetails(){
        _uiState.update {
            it.copy(
                splitDaysList = emptyList(),
                startDate = LocalDate.now(),
                isActive = false,
                splitDay = null
            )
        }
    }
}