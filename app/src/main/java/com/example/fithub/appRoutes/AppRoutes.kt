package com.example.fithub.appRoutes

object AppRoutes {
    const val HOME_ROUTE = "home"
    const val ADD_WEIGHT_ROUTE = "add_weight"
    const val MANAGE_WORKOUT_SPLIT_ROUTE = "manage_workout_split"
    const val ADD_WORKOUT_SPLIT_ROUTE = "add_workout_split"
    const val ADD_WORKOUT_DAY_ROUTE = "add_workout_day"
    const val MANAGE_SPLIT_DAYS_ROUTE = "manage_split_days"
    const val MANAGE_EXERCISES_ROUTE = "manage_exercises"
    const val ADD_EXERCISE_ROUTE = "add_exercise"
    const val SPLIT_ID_ARGUMENT = "splitId"
    const val EXERCISE_ID_ARGUMENT = "exerciseId"
    const val EDIT_EXERCISE_ROUTE = "editExercise"

    const val MANAGE_SPLIT_DAYS_WITH_ARGUMENT_ROUTE = "$MANAGE_SPLIT_DAYS_ROUTE/{$SPLIT_ID_ARGUMENT}"
    const val EDIT_EXERCISE_WITH_ARGUMENT_ROUTE = "$EDIT_EXERCISE_ROUTE/{$EXERCISE_ID_ARGUMENT}"

    fun manageSplitDaysRoute(splitId: Long): String = "$MANAGE_SPLIT_DAYS_ROUTE/$splitId"
    fun editExerciseRoute(exerciseId: Long) = "$EDIT_EXERCISE_ROUTE/$exerciseId"
}