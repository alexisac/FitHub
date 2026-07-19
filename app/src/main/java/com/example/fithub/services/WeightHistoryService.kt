package com.example.fithub.services

import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ServiceMessages
import com.example.fithub.repositories.WeightHistoryRepository
import com.example.fithub.roomDB.entities.WeightHistoryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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

        if (weightValue <= 0) {
            throw ValidationException(ServiceMessages.WEIGHT_POSITIVE_NUMBER)
        }

        val localDate = try {
            LocalDate.parse(date)
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