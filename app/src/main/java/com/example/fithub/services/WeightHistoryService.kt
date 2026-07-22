package com.example.fithub.services

import com.example.fithub.common.Constants
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ServiceMessages
import com.example.fithub.repositories.WeightHistoryRepository
import com.example.fithub.roomDB.entities.WeightHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WeightHistoryService @Inject constructor(
    private val weightHistoryRepository: WeightHistoryRepository
) {
    suspend fun addWeight(
        weight: String,
        date: String,
        time: String
    ): Long {
        val weightValue = weight.toDoubleOrNull()
            ?: throw ValidationException(ServiceMessages.WEIGHT_VALID_NUMBER)

        if (weightValue !in Constants.WEIGHT_MIN_LIMIT..Constants.WEIGHT_MAX_LIMIT) {
            throw ValidationException(ServiceMessages.WEIGHT_LIMIT)
        }

        val localDate = try {
            LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
            )
        } catch (_: Exception) {
            throw ValidationException(ServiceMessages.SELECT_VALID_DATE)
        }

        val localTime = try {
            LocalTime.parse(time)
        } catch (_: Exception) {
            throw ValidationException(ServiceMessages.SELECT_VALID_TIME)
        }

        val dateTime = LocalDateTime.of(localDate, localTime)

        val weightHistory = WeightHistoryEntity(
            dateTime = dateTime,
            weight = weightValue
        )

        return weightHistoryRepository.addWeight(weightHistory)
    }

    fun getWeightHistory(): Flow<List<WeightHistoryEntity>> {
        return weightHistoryRepository.getWeightsHistory()
    }
}