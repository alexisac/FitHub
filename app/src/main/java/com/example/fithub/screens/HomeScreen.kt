package com.example.fithub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.screens.reusableComponents.ThemeToggle
import com.example.fithub.viewModels.WeightViewModel

@Composable
fun HomeScreen (
    viewModel: WeightViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    goToAddWeightMenu: () -> Unit,
    goToManageWorkoutSplit: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val isDark = isDarkTheme

    LaunchedEffect(Unit) {
        viewModel.testDB()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        ThemeToggle(
            isDarkTheme = isDark,
            onThemeChange = onThemeChange,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 42.dp,
                    end = 24.dp
                )
        )

        Button(
//            onClick = goToAddWeightMenu,
            onClick = goToManageWorkoutSplit,
        ) {
            Text(ScreenMessages.ADD_WEIGHT_BUTTON)
        }

        Text(
            text = when {
                uiState.isLoading -> "Loading..."
                uiState.errorMessage != null -> uiState.errorMessage
                else -> uiState.message ?: "no data"
            }.orEmpty()
        )
    }
}

