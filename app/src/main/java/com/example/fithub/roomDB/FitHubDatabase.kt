package com.example.fithub.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fithub.roomDB.converters.DayTypeConverter
import com.example.fithub.roomDB.converters.LocalDateConverter
import com.example.fithub.roomDB.converters.LocalDateTimeConverter
import com.example.fithub.roomDB.dao.WeightHistoryDao
import com.example.fithub.roomDB.dao.WorkoutDao
import com.example.fithub.roomDB.entities.WeightHistoryEntity
import com.example.fithub.roomDB.entities.WorkoutSplitDayEntity
import com.example.fithub.roomDB.entities.WorkoutSplitEntity

@Database(
    entities = [
        WeightHistoryEntity::class,
        WorkoutSplitEntity::class,
        WorkoutSplitDayEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    LocalDateConverter::class,
    DayTypeConverter::class
)
abstract class FitHubDatabase : RoomDatabase() {
    abstract fun weightHistoryDao(): WeightHistoryDao

    abstract fun workoutDao(): WorkoutDao
}