package com.example.fithub.roomDB.converters

import androidx.room.TypeConverter
import com.example.fithub.models.MuscleGroup

class MuscleGroupConverter {
    @TypeConverter
    fun fromMuscleGroup(value: MuscleGroup?): String? = value?.name

    @TypeConverter
    fun toMuscleGroup(value: String?): MuscleGroup? = value?.let(MuscleGroup::valueOf)
}