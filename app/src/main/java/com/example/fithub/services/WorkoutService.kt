package com.example.fithub.services

import com.example.fithub.common.Constants
import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ServiceMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.repositories.WorkoutRepository
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend fun createSplit(
        splitName: String,
        selectedDate: String,
        splitDaysList: List<WorkoutSplitDay>
    ): Long {
        if (splitName.isBlank()) {
            throw ValidationException(ServiceMessages.SPLIT_NAME_NOT_EMPTY)
        }

        if (splitName.trim().length !in 1..50) {
            throw ValidationException(ServiceMessages.SPLIT_NAME_LIMIT)
        }

        if (selectedDate.isBlank()) {
            throw ValidationException(ServiceMessages.START_DATE_NOT_EMPTY)
        }

        if (splitDaysList.isEmpty()) {
            throw ValidationException(ServiceMessages.ADD_DAY_TO_SPLIT)
        }

        val startDate = try {
            LocalDate.parse(
                selectedDate,
                DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
            )
        } catch (_: Exception) {
            throw IllegalArgumentException(ServiceMessages.INVALID_START_DATE)
        }

        val splitEntity = WorkoutSplitEntity(
            name = splitName.trim(),
            startDate = startDate,
            active = false
        )

        val dayEntities = splitDaysList.mapIndexed { index, day ->
            WorkoutSplitDayEntity(
                workoutSplitId = 0,
                name = day.name.trim(),
                day = day.day,
                position = index + 1
            )
        }

        return workoutRepository.createSplit(
            split = splitEntity,
            days = dayEntities
        )
    }

    fun createWorkoutDay(
        name: String,
        dayType: DayType,
        position: Int
    ): WorkoutSplitDay {
        if (name.isBlank()) {
           throw ValidationException(ServiceMessages.DAY_NAME_NOT_EMPTY)
        }

        if (name.trim().length !in 1..50) {
            throw ValidationException(ServiceMessages.DAY_NAME_LIMIT)
        }

        return WorkoutSplitDay(
            // Temporary ID used for reorderable list
            id = System.currentTimeMillis(),
            name = name.trim(),
            day = dayType,
            position = position
        )
    }
}