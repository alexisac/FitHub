package com.example.fithub.common.messages

object ScreenMessages {
    // Buttons
    const val ADD_WEIGHT_BUTTON = "Add weight"
    const val ADD_DAY_BUTTON = "Add day"
    const val CREATE_EXERCISE_BUTTON = "Create exercise"
    const val CREATE_SPLIT_BUTTON = "Create split"
    const val DELETE_EXERCISE_TITLE = "Delete exercise"
    const val MANAGE_EXERCISES_BUTTON = "Manage exercises"
    const val MANAGE_WORKOUT_BUTTON = "Manage workout"
    const val SAVE_BUTTON = "Save"
    const val UPDATE_EXERCISE_BUTTON = "Update exercise"


    // Content descriptions
    const val ADD_DESCRIPTION = "Add"
    const val BACK_DESCRIPTION = "Back"
    const val CALENDAR_DESCRIPTION = "Calendar"
    const val CLEAR_DESCRIPTION = "Clear"
    const val CLOSE_DESCRIPTION = "Close"
    const val DARK_MODE_DESCRIPTION = "DarkMode"
    const val DELETE_DESCRIPTION = "Delete"
    const val EDIT_DESCRIPTION = "Edit"
    const val ERROR_DESCRIPTION = "Error"
    const val DESCRIPTION_DESCRIPTION = "Exercise description"
    const val INFO_DESCRIPTION = "Info"
    const val LIGHT_MODE_DESCRIPTION = "LightMode"
    const val LIST_DESCRIPTION = "List"
    const val MENU_DESCRIPTION = "Menu"
    const val REORDER_DESCRIPTION = "Reorder"
    const val REST_DAY_DESCRIPTION = "RestDay"
    const val SEARCH_DESCRIPTION = "Search"
    const val SELECTED_DESCRIPTION = "Selected"
    const val SPORT_DESCRIPTION = "Sport"
    const val START_DATE_DESCRIPTION = "Starts: "
    const val SUCCESS_DESCRIPTION = "Success"
    const val THEME_TOGGLE_OFFSET_DESCRIPTION = "ThemeToggleOffset"
    const val WATCH_DESCRIPTION = "Watch"
    const val WEIGHT_DESCRIPTION = "Weight"
    const val WORKOUT_DAY_DESCRIPTION = "WorkoutDay"


    // Titles
    const val ADD_DAY_TITLE = "Add day"
    const val ADD_EXERCISE_TITLE = "Add exercise"
    const val ADD_WEIGHT_TITLE = "Add weight"
    const val ADD_WORKOUT_SPLIT_TITLE = "Add workout split"
    const val DARK_THEME_TITLE = "Dark theme"
    const val DATE_TITLE = "Date"
    const val DATE_AND_TIME_TITLE = "Date and time"
    const val DAY_NAME_TITLE = "Day name"
    const val DAY_TYPE_TITLE = "Day type"
    const val DAYS_IN_SPLIT_TITLE = "Days in split"
    const val EDIT_EXERCISE_TITLE = "Edit exercise"
    const val ERROR_TITLE = "Error"
    const val EXERCISE_DESCRIPTION_TITLE = "Exercise description"
    const val EXERCISE_NAME_TITLE = "Exercise name"
    const val MANAGE_EXERCISES_TITLE = "Manage exercises"
    const val MANAGE_SPLIT_DETAILS_TITLE = "Manage split details"
    const val MANAGE_SPLIT_DAYS_TITLE = "Manage split days: "
    const val MANAGE_WORKOUT_SPLIT_TITLE = "Manage workout split"
    const val MENU_TITLE = "Menu"
    const val MUSCLE_GROUP_TITLE = "Muscle group"
    const val REST_TITLE = "Rest"
    const val SPLIT_DETAILS_TITLE = "Split details"
    const val SPLIT_NAME_TITLE = "Split name"
    const val START_DATE_TITLE = "Start date"
    const val STATUS_TITLE = "Status"
    const val SUCCESS_TITLE = "Success"
    const val TIME_TITLE = "Time"
    const val WEIGHT_TITLE = "Weight"
    const val WEIGHT_PROGRESS_TITLE = "Weight progress"
    const val WORKOUT_TITLE = "Workout"


    // Subtitles
    const val ADD_DAY_SUBTITLE = "Add a workout or rest day to your split."
    const val ADD_EXERCISE_SUBTITLE = "Create and save a new exercise."
    const val ADD_WORKOUT_SPLIT_SUBTITLE = "Create a new split and define your training days."
    const val DAYS_IN_SPLIT_SUBTITLE = "Add the days that make up this split."
    const val EDIT_EXERCISE_SUBTITLE = "Edit and save modifications."
    const val MANAGE_EXERCISES_SUBTITLE = "View, update or delete your exercises."
    const val MANAGE_SPLIT_DAY_EXERCISES_SUBTITLE = "Select the exercises for this workout day."
    const val MANAGE_SPLIT_DETAILS_SUBTITLE = "Manage days and split settings."
    const val MUSCLE_GROUP_SUBTITLE = "Choose the primary muscle group."
    const val RECORD_WEIGHT_SUBTITLE = "Record your weight to track your progress."
    const val WORKOUT_SPLIT_SUBTITLE = "View, update or delete your workout splits."
    fun weightProgressSubtitle(days: String): String = "Daily average for the last $days days"


    // Placeholders
    const val BENCH_PRESS_PLACEHOLDER = "e.g. Bench press"
    const val CHEST_PLACEHOLDER = "e.g. Chest"
    const val EXERCISE_DESCRIPTION_PLACEHOLDER = "e.g. Technique tips, equipment or additional notes."
    const val SEARCH_EXERCISES_PLACEHOLDER = "Search exercises"
    const val SELECT_DATE_PLACEHOLDER = "Select date"
    const val SELECT_TIME_PLACEHOLDER = "Select time"
    const val SPLIT_NAME_PLACEHOLDER = "e.g. Push Pull Legs"
    const val WORKOUT_DAY_PLACEHOLDER = "e.g. Workout day"


    // Common
    const val ACTIVE = "Active"
    const val CANCEL = "Cancel"
    const val EMPTY_DATE = "-"
    const val INACTIVE = "Inactive"
    const val OK = "OK"
    const val KG = "KG"
    const val NO = "No"
    const val YES = "Yes"


    // Others
    const val ACTIVATE_SPLIT_CONFIRMATION_MESSAGE = "Are you sure you want to make this workout split active?"
    const val ACTIVATE_WORKOUT = "Activate workout split?"
    const val WEIGHT_TIP = "Tip: Weigh yourself at the same time each day for more accurate tracking."
    const val REORDER_DAYS_TIP = "Tip: You can reorder days later by dragging them."
    const val NO_EXERCISES_FOUND = "No exercises found."
    const val NO_WEIGHT_RECORDS_AVAILABLE = "No weight records available."
    fun deleteExerciseMessage(exerciseName: String): String = "Are you sure you want to delete \"$exerciseName\"?"

}