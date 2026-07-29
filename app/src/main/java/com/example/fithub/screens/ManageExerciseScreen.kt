package com.example.fithub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.screens.reusableComponents.Header
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.ExerciseViewModel

@Composable
fun ManageExerciseScreen(
    exerciseViewModel: ExerciseViewModel,
    isDarkTheme: Boolean,
    goToAddExercise: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by exerciseViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Header(
            title = ScreenMessages.MANAGE_EXERCISES_TITLE,
            subtitle = ScreenMessages.MANAGE_EXERCISES_SUBTITLE,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onBack = onBack,
            rightIcon = Icons.Outlined.Add,
            rightIconDescription = ScreenMessages.ADD_DESCRIPTION,
            onRightIconClick = goToAddExercise
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}