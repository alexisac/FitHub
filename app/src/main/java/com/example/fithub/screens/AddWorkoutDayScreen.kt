package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.DayType
import com.example.fithub.screens.reusableComponents.ErrorPopupMessage
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel

@Composable
fun AddWorkoutDayScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    onBack: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    var selectedDay by remember { mutableStateOf(DayType.WORKOUT) }
    var dayName by remember { mutableStateOf("") }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
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
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        DayTypeToggle(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            selectedContainerColor = colors.selectedContainer,
            selectedItemColor = colors.primary,
            selectedDay = selectedDay,
            onDaySelected = { dayType ->
                selectedDay = dayType
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        DayNameField(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            dayName = dayName,
            onDayName = { newName ->
                dayName = newName
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TipSection(
            informationColor = colors.secondary,
            borderColor = colors.secondary,
            containerColor = colors.infoContainer
        )

        Spacer(modifier = Modifier.height(24.dp))

        AddDayButton(
            textColor = colors.onPrimary,
            buttonColor = colors.primary,
            onClick = {
                workoutViewModel.addWorkoutDay(
                    name = dayName,
                    dayType = selectedDay
                )
            }
        )

        uiState.errorMessage?.let { error ->
            ErrorPopupMessage(
                message = error,
                isErrorMessage = true,
                isDarkTheme = isDarkTheme,
                onDismiss = {
                    workoutViewModel.clearMessages()
                }
            )
        }
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
            text = ScreenMessages.ADD_DAY_TITLE,
            color = primaryTextColor,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = ScreenMessages.ADD_DAY_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun DayTypeToggle(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    selectedContainerColor: Color,
    selectedItemColor: Color,
    selectedDay: DayType,
    onDaySelected: (DayType) -> Unit
) {
    Column {
        Text(
            text = ScreenMessages.DAY_TYPE_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val isWorkoutSelected = selectedDay == DayType.WORKOUT

            OutlinedButton(
                onClick = {
                    onDaySelected(DayType.WORKOUT)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isWorkoutSelected) {
                        selectedItemColor
                    } else {
                        borderColor
                    }
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isWorkoutSelected) {
                        selectedContainerColor
                    } else {
                        containerColor
                    },
                    contentColor = if (isWorkoutSelected) {
                        selectedItemColor
                    } else {
                        secondaryTextColor
                    }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FitnessCenter,
                        contentDescription = ScreenMessages.WORKOUT_DAY_DESCRIPTION,
                        tint = if (isWorkoutSelected) {
                            selectedItemColor
                        } else {
                            secondaryTextColor
                        }
                    )

                    Text(
                        text = ScreenMessages.WORKOUT_TITLE,
                        color = if (isWorkoutSelected) {
                            selectedItemColor
                        } else {
                            secondaryTextColor
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            val isRestSelected = selectedDay == DayType.REST_DAY

            OutlinedButton(
                onClick = {
                    onDaySelected(DayType.REST_DAY)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isRestSelected) {
                        selectedItemColor
                    } else {
                        borderColor
                    }
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isRestSelected) {
                        selectedContainerColor
                    } else {
                        containerColor
                    },
                    contentColor = if (isRestSelected) {
                        selectedItemColor
                    } else {
                        secondaryTextColor
                    }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Bed,
                        contentDescription = ScreenMessages.REST_DAY_DESCRIPTION,
                        tint = if (isRestSelected) {
                            selectedItemColor
                        } else {
                            secondaryTextColor
                        }
                    )

                    Text(
                        text = ScreenMessages.REST_TITLE,
                        color = if (isRestSelected) {
                            selectedItemColor
                        } else {
                            secondaryTextColor
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun DayNameField(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    dayName: String,
    onDayName: (String) -> Unit
) {
    Column {
        Text(
            text = ScreenMessages.DAY_NAME_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dayName,
            onValueChange = onDayName,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = ScreenMessages.CHEST_PLACEHOLDER,
                    color = secondaryTextColor
                )
            },
            textStyle = LocalTextStyle.current.copy(
                color = primaryTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.List,
                    contentDescription = ScreenMessages.LIST_DESCRIPTION,
                    tint = iconColor
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = iconColor,
                unfocusedBorderColor = borderColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedTextColor = primaryTextColor,
                unfocusedTextColor = primaryTextColor,
                cursorColor = iconColor
            )
        )
    }
}

@Composable
private fun TipSection(
    informationColor: Color,
    borderColor: Color,
    containerColor: Color
) {
    OutlinedCard(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor,
            contentColor = informationColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 76.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = ScreenMessages.INFO_DESCRIPTION,
                tint = informationColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = ScreenMessages.REORDER_DAYS_TIP,
                color = informationColor,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun AddDayButton(
    textColor: Color,
    buttonColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = buttonColor,
            contentColor = textColor,
            disabledContainerColor = buttonColor,
            disabledContentColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        Text(
            text = ScreenMessages.ADD_DAY_BUTTON,
            color = textColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}