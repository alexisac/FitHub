package com.example.fithub.roomDB

import android.content.Context
import androidx.room.Room
import com.example.fithub.roomDB.dao.WeightHistoryDao
import com.example.fithub.roomDB.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFitHubDatabase(@ApplicationContext context: Context): FitHubDatabase {
        return Room.databaseBuilder(
            context,
            FitHubDatabase::class.java,
            "fithub_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWeightHistoryDao(database: FitHubDatabase): WeightHistoryDao {
        return database.weightHistoryDao()
    }

    @Provides
    fun provideWorkoutDao(database: FitHubDatabase): WorkoutDao {
        return database.workoutDao()
    }
}