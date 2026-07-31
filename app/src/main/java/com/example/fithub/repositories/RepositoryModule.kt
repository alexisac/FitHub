package com.example.fithub.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeightHistoryRepository(
        implementation: WeightHistoryRepositoryImpl
    ): WeightHistoryRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        implementation: WorkoutRepositoryImpl
    ): WorkoutRepository

    @Binds
    @Singleton
    abstract fun bindExerciseRepository(
        implementation: ExerciseRepositoryImpl
    ): ExerciseRepository

    @Binds
    @Singleton
    abstract fun bindWorkoutSplitDayExercisesRepository(
        implementation: WorkoutSplitDayExercisesImpl
    ): WorkoutSplitDayExercisesRepository
}