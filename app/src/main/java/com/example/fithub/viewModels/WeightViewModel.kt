package com.example.fithub.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithub.common.Constants
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ViewModelErrorMessages
import com.example.fithub.common.messages.ViewModelSuccessMessages
import com.example.fithub.models.WeightChartPoint
import com.example.fithub.models.uiStates.WeightUiState
import com.example.fithub.services.WeightHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val weightHistoryService: WeightHistoryService
): ViewModel(){
    private val _uiState = MutableStateFlow(WeightUiState())
    val uiState: StateFlow<WeightUiState> = _uiState.asStateFlow()

    init {
        getWeightChart()
    }

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
                        successMessage = ViewModelSuccessMessages.WEIGHT_ADDED_SUCCESSFULLY
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

    private fun getWeightChart() {
        viewModelScope.launch {
            weightHistoryService.getWeightHistory().collect { history ->
                val today = LocalDate.now()
                val firstDay = today.minusDays(Constants.WEIGHT_CHART_DAYS)

                val dailyAverageWeights = history
                    .filter { record ->
                        val recordDate = record.dateTime.toLocalDate()
                        recordDate in firstDay..today
                    }
                    .groupBy { record ->
                        record.dateTime.toLocalDate()
                    }
                    .mapValues { (_, record) ->
                        record
                            .map { it.weight }
                            .average()
                    }

                val chart = (0L..Constants.WEIGHT_CHART_DAYS).map { dayOffset ->
                    val date = firstDay.plusDays(dayOffset)

                    WeightChartPoint(
                        date = date,
                        weight = dailyAverageWeights[date]
                    )
                }

                _uiState.update {
                    it.copy(
                        weightChart = chart
                    )
                }
            }
        }
    }
}