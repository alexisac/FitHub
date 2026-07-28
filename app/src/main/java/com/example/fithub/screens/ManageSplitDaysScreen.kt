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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        workoutViewModel.getSplitById(splitId)
    }

    LaunchedEffect(uiState.successMessage) {
        if(uiState.successMessage != null) {
            workoutViewModel.clearSplitDetails()
            workoutViewModel.clearMessages()
            onBack()
        }
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
                workoutViewModel.clearSplitDetails()
                onBack()
            },
            onDelete = {
                workoutViewModel.deleteSplit(splitId)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SplitDetails(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            startDate = uiState.startDate,
            isActive = uiState.isActive,
            onStatusClick = {
                workoutViewModel.updateSplitDetails(splitId)
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
    onBack: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
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
                onClick = onDelete,
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
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = ScreenMessages.DELETE_DESCRIPTION
                )
            }
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
private fun SplitDetails(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    startDate: LocalDate,
    isActive: Boolean,
    onStatusClick: () -> Unit
) {
    var showStatusDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = ScreenMessages.SPLIT_DETAILS_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SplitDetailCard(
                modifier = Modifier
                    .weight(1f),
                title = ScreenMessages.START_DATE_TITLE,
                value = if (isActive) {
                    startDate.format(
                        DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
                    )
                } else {
                    ScreenMessages.EMPTY_DATE
                },
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                containerColor = containerColor,
                accentColor = iconColor
            )

            SplitDetailCard(
                modifier = Modifier
                    .weight(1f),
                title = ScreenMessages.STATUS_TITLE,
                value = if (isActive) {
                    ScreenMessages.ACTIVE
                } else {
                    ScreenMessages.INACTIVE
                },
                primaryTextColor = if (isActive) {
                    iconColor
                } else {
                    secondaryTextColor
                },
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                containerColor = containerColor,
                accentColor = if (isActive) {
                    iconColor
                } else {
                    secondaryTextColor
                },
                onClick = if (!isActive) {
                    {
                    showStatusDialog = true
                    }
                } else {
                    null
                }
            )
        }
    }

    if (showStatusDialog) {
        ActivateSplitDialog(
            primaryTextColor = primaryTextColor,
            secondaryTextColor = secondaryTextColor,
            containerColor = containerColor,
            confirmColor = iconColor,
            onConfirm = {
                onStatusClick()
                showStatusDialog = false
            },
            onDismiss = {
                showStatusDialog = false
            }
        )
    }
}

@Composable
private fun SplitDetailCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    accentColor: Color,
    onClick: (() -> Unit)? = null
) {
    OutlinedCard(
        onClick = {
            onClick?.invoke()
        },
        enabled = onClick != null,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = secondaryTextColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                color = primaryTextColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(3.dp)
                    .background(
                        color = accentColor,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

@Composable
private fun ActivateSplitDialog(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    containerColor: Color,
    confirmColor: Color,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = containerColor,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = ScreenMessages.ACTIVATE_WORKOUT,
                color = primaryTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = ScreenMessages.ACTIVATE_SPLIT_CONFIRMATION_MESSAGE,
                color = secondaryTextColor,
                fontSize = 16.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = ScreenMessages.YES,
                    color = confirmColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = ScreenMessages.NO,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
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