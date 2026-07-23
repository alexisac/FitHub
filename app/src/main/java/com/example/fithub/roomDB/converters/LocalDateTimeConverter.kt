package com.example.fithub.roomDB.converters

import androidx.room.TypeConverter
import com.example.fithub.common.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.format(
        DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)
    )

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let{
        LocalDateTime.parse(
            it,
            DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)
        )
    }
}