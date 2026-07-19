package com.example.fithub.services

import com.example.fithub.exceptions.ValidationException
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
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    suspend fun createSplit(
        splitName: String,
        selectedDate: String,
        splitDaysList: List<WorkoutSplitDay>
    ): Long {
        if (splitName.isBlank()) {
            throw ValidationException("Split name cannot be empty.")
        }

        if (selectedDate.isBlank()) {
            throw ValidationException("Start date cannot be empty.")
        }

        if (splitDaysList.isEmpty()) {
            throw ValidationException("Add at least one day to the split.")
        }

        val startDate = try {
            LocalDate.parse(
                selectedDate,
                dateFormatter
            )
        } catch (_: Exception) {
            throw IllegalArgumentException("Invalid start date.")
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

}