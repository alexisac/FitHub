package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.WorkoutSplit
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ManageWorkoutSplitsScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    goToAddWorkout: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Header(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            goToAddWorkout = {
                goToAddWorkout()
            },
            onBack = {
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SplitList(
            splits = uiState.splits,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            primaryColor = colors.primary,
            successColor = colors.success
        )
    }
}

@Composable
private fun Header(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    goToAddWorkout: () -> Unit,
    onBack: () -> Unit
) {
    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedIconButton(
                onClick = onBack,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = containerColor,
                    contentColor = iconColor
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = ScreenMessages.BACK_DESCRIPTION
                )
            }

            OutlinedIconButton(
                onClick = goToAddWorkout,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = containerColor,
                    contentColor = iconColor
                ),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = ScreenMessages.ADD_DESCRIPTION
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = ScreenMessages.MANAGE_WORKOUT_SPLIT_TITLE,
            color = primaryTextColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = ScreenMessages.WORKOUT_SPLIT_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SplitList(
    splits: List<WorkoutSplit>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    primaryColor: Color,
    successColor: Color
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(splits) { split ->
            SplitCard(
                split = split,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                containerColor = containerColor,
                primaryColor = primaryColor,
                successColor = successColor
            )
        }
    }
}

@Composable
private fun SplitCard(
    split: WorkoutSplit,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    primaryColor: Color,
    successColor: Color
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = split.name,
                    color = primaryTextColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${ScreenMessages.START_DATE_DESCRIPTION} ${split.startDate.format(
                        DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
                    )}",
                    color = secondaryTextColor,
                    fontSize = 15.sp
                )
            }

            Text(
                text = if (split.active)
                    ScreenMessages.ACTIVE
                else
                    ScreenMessages.INACTIVE,
                color = if (split.active)
                    successColor
                else
                    primaryColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}