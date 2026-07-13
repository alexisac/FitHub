package com.example.fithub.appRoutes

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fithub.screens.HomeScreen
import com.example.fithub.viewModels.HomeViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val homeViewModel: HomeViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME_ROUTE
    ){
        composable(AppRoutes.HOME_ROUTE) {
            HomeScreen(
                viewModel = homeViewModel
            )
        }
    }
}