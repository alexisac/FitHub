package com.example.fithub.roomDB.converters

import androidx.room.TypeConverter
import com.example.fithub.models.DayType

class DayTypeConverter {
    @TypeConverter
    fun fromDayType(value: DayType?): String? = value?.name

    @TypeConverter
    fun toDayType(value: String?): DayType? = value?.let(DayType::valueOf)
}