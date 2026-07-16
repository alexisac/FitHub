package com.example.fithub.services

import com.example.fithub.exceptions.ValidationException
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
            ?: throw ValidationException("Weight must be a valid number\n")

        if (weightValue <= 0) {
            throw ValidationException("Weight must be greater than 0\n")
        }

        val localDate = try {
            LocalDate.parse(date)
        } catch (_: Exception) {
            throw ValidationException("Please select a valid time\n")
        }

        val localTime = try {
            LocalTime.parse(time)
        } catch (_: Exception) {
            throw ValidationException("Please select a valid time\n")
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