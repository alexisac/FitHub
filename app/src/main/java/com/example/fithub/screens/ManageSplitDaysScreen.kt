package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel

@Composable
fun ManageSplitDaysScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    splitId: Long,
    onBack: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    LaunchedEffect(splitId) {
        workoutViewModel.getAllSplitDays(splitId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Header(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onBack = {
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SplitDaysList(
            splitDays = uiState.splitDaysList,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            workoutColor = colors.primary,
            restDayColor = colors.secondaryText,
            circleTextColor = colors.onPrimary
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
    onBack: () -> Unit
) {
    Column {
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = ScreenMessages.MANAGE_SPLIT_DETAILS_TITLE,
            color = primaryTextColor,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = ScreenMessages.MANAGE_SPLIT_DETAILS_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun SplitDaysList(
    splitDays: List<WorkoutSplitDay>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    circleTextColor: Color
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(splitDays) {splitDay ->
            SplitDayCard(
                splitDay = splitDay,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                containerColor = containerColor,
                workoutColor = workoutColor,
                restDayColor = restDayColor,
                circleTextColor = circleTextColor
            )
        }
    }
}

@Composable
private fun SplitDayCard(
    splitDay: WorkoutSplitDay,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    circleTextColor: Color
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
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
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = if (splitDay.day == DayType.WORKOUT) {
                            workoutColor
                        } else {
                            restDayColor
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = splitDay.position.toString(),
                    color = circleTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = splitDay.name,
                    color = primaryTextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = splitDay.day.toString(),
                    color = secondaryTextColor,
                    fontSize = 14.sp
                )
            }
        }
    }
}