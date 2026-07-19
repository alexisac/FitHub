package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel

@Composable
fun ManageWorkoutSplitsScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    goToAddWorkout: () -> Unit,
    onBack: () -> Unit
) {
    //val uiState by viewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Header(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            iconColor = colors.icon,
            borderColor = colors.secondaryBorderColor,
            goToAddWorkout = {
                goToAddWorkout()
            },
            onBack = {
                onBack()
            }
        )
    }
}

@Composable
private fun Header(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    iconColor: Color,
    borderColor: Color,
    goToAddWorkout: () -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedIconButton(
            onClick = onBack,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            ),
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = iconColor)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Manage workout split",
                color = primaryTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "View, update or delete your workout splits.",
                color = secondaryTextColor,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        OutlinedIconButton(
            onClick = goToAddWorkout,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            ),
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = iconColor),
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add"
            )
        }
    }
}