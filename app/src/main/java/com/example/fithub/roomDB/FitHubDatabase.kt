package com.example.fithub.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fithub.roomDB.converters.LocalDateTimeConverter
import com.example.fithub.roomDB.dao.WeightHistoryDao
import com.example.fithub.roomDB.entities.WeightHistoryEntity

@Database(
    entities = [
        WeightHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class FitHubDatabase : RoomDatabase() {
    abstract fun weightHistoryDao(): WeightHistoryDao
}