package com.example.fithub.appRoutes

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fithub.screens.AddWeightScreen
import com.example.fithub.screens.AddWorkoutDayScreen
import com.example.fithub.screens.AddWorkoutSplitScreen
import com.example.fithub.screens.HomeScreen
import com.example.fithub.screens.ManageWorkoutSplitsScreen
import com.example.fithub.viewModels.WeightViewModel
import com.example.fithub.viewModels.WorkoutViewModel

@Composable
fun AppNavHost(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    val weightViewModel: WeightViewModel = hiltViewModel()
    val workoutViewModel: WorkoutViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME_ROUTE
    ){
        composable(AppRoutes.HOME_ROUTE) {
            HomeScreen(
                viewModel = weightViewModel,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                goToAddWeightMenu = {
                    navController.navigate(AppRoutes.ADD_WEIGHT_ROUTE)
                },
                goToManageWorkoutSplit = {
                    navController.navigate(AppRoutes.MANAGE_WORKOUT_SPLIT_ROUTE)
                }
            )
        }

        composable(AppRoutes.ADD_WEIGHT_ROUTE) {
            AddWeightScreen(
                viewModel = weightViewModel,
                isDarkTheme = isDarkTheme,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.MANAGE_WORKOUT_SPLIT_ROUTE) {
            ManageWorkoutSplitsScreen(
                workoutViewModel = workoutViewModel,
                isDarkTheme = isDarkTheme,
                goToAddWorkout = {
                    navController.navigate(AppRoutes.ADD_WORKOUT_SPLIT_ROUTE)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.ADD_WORKOUT_SPLIT_ROUTE) {
            AddWorkoutSplitScreen(
                workoutViewModel = workoutViewModel,
                isDarkTheme = isDarkTheme,
                goToAddWorkoutDay = {
                    navController.navigate(AppRoutes.ADD_WORKOUT_DAY_ROUTE)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.ADD_WORKOUT_DAY_ROUTE) {
            AddWorkoutDayScreen(
                workoutViewModel = workoutViewModel,
                isDarkTheme = isDarkTheme,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}