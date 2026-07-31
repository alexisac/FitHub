package com.example.fithub.services

import com.example.fithub.common.exceptions.ValidationException
import com.example.fithub.common.messages.ServiceMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplit
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.repositories.WorkoutRepository
import java.time.LocalDate
import javax.inject.Inject

class WorkoutService @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend fun createSplit(
        splitName: String,
        selectedDate: LocalDate,
        splitDaysList: List<WorkoutSplitDay>
    ): Long {
        if (splitName.isBlank()) {
            throw ValidationException(ServiceMessages.SPLIT_NAME_NOT_EMPTY)
        }

        if (splitName.trim().length !in 1..50) {
            throw ValidationException(ServiceMessages.SPLIT_NAME_LIMIT)
        }

        if (splitDaysList.isEmpty()) {
            throw ValidationException(ServiceMessages.ADD_DAY_TO_SPLIT)
        }

        val split = WorkoutSplit(
            id = 0,
            name = splitName.trim(),
            startDate = selectedDate,
            active = false
        )

        val day = splitDaysList.mapIndexed { index, day ->
            day.copy(
                name = day.name.trim(),
                position = index + 1
            )
        }

        return workoutRepository.createSplit(
            split = split,
            days = day
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
            workoutSplitId = -1,
            name = name.trim(),
            day = dayType,
            position = position
        )
    }

    suspend fun getAllSplits(): List<WorkoutSplit> {
        return workoutRepository.getAllSplits()
    }

    suspend fun getDaysForSplit(splitId: Long): List<WorkoutSplitDay> =
        workoutRepository.getDaysForSplit(splitId)

    suspend fun getSplitById(splitId: Long): WorkoutSplit =
        workoutRepository.getSplitById(splitId)

    suspend fun getSplitDayById(splitDayId: Long): WorkoutSplitDay =
        workoutRepository.getSplitDayById(splitDayId)

    suspend fun setActiveSplit(splitId: Long) =
        workoutRepository.setActiveSplit(splitId)

    suspend fun updateStartDate(splitId: Long, startDate: LocalDate) =
        workoutRepository.updateStartDate(splitId, startDate)

    suspend fun deleteSplit(splitId: Long) =
        workoutRepository.deleteSplit(splitId)
}