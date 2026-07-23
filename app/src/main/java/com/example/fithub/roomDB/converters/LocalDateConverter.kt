package com.example.fithub.roomDB.converters

import androidx.room.TypeConverter
import com.example.fithub.common.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.format(
        DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
    )

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let{
        LocalDate.parse(
            it,
            DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
        )
    }
}