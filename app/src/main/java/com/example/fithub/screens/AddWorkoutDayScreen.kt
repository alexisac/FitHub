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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ButtonColors
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
import com.example.fithub.models.DayType
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
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Header(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.secondaryBorderColor,
            iconColor = colors.icon,
            onBack = {
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DayTypeToggle(
            primaryTextColor = colors.primaryText,
            selectedItemColor = colors.icon,
            unselectedItemColor = colors.secondaryText,
            selectedDay = selectedDay,
            onDaySelected = { dayType ->
                selectedDay = dayType
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DayNameField(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.primaryBorderColor,
            dayName = dayName,
            onDayName = { newName ->
                dayName = newName
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TipSection(
            textColor = colors.primaryText,
            borderColor = colors.primaryBorderColor,
            iconColor = colors.icon
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddDayButton(
            textColor = colors.primaryText,
            buttonColor = colors.icon,
            onClick = {
                workoutViewModel.addWorkoutDay(
                    name = dayName,
                    dayType = selectedDay
                )
            }
        )

        uiState.errorMessage?.let { error ->

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = error,
                color = colors.error,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun Header(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    iconColor: Color,
    onBack: () -> Unit
) {
    Row {
        OutlinedIconButton(
            onClick = onBack,
            shape = RoundedCornerShape(16.dp),
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

        Spacer(modifier = Modifier.width(16.dp))

        Column{
            Text(
                text = "Add day",
                color = primaryTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Add a workout or rest day to your split.",
                color = secondaryTextColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun DayTypeToggle(
    primaryTextColor: Color,
    selectedItemColor: Color,
    unselectedItemColor: Color,
    selectedDay: DayType,
    onDaySelected: (DayType) -> Unit
) {
    Column {
        Text(
            text = "DAY TYPE",
            color = primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    onDaySelected(DayType.WORKOUT)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 0.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 0.dp
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedDay == DayType.WORKOUT) {
                        selectedItemColor
                    } else {
                        unselectedItemColor
                    }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FitnessCenter,
                        contentDescription = "WorkoutDay",
                        tint = if (selectedDay == DayType.WORKOUT) {
                            selectedItemColor
                        } else {
                            unselectedItemColor
                        }
                    )

                    Text(
                        text = "WORKOUT",
                        color = if (selectedDay == DayType.WORKOUT) {
                            selectedItemColor
                        } else {
                            unselectedItemColor
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            OutlinedButton(
                onClick = {
                    onDaySelected(DayType.REST_DAY)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 16.dp
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedDay == DayType.REST_DAY) {
                        selectedItemColor
                    } else {
                        unselectedItemColor
                    }
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Bed,
                        contentDescription = "RestDay",
                        tint = if (selectedDay == DayType.REST_DAY) {
                            selectedItemColor
                        } else {
                            unselectedItemColor
                        }
                    )

                    Text(
                        text = "REST",
                        color = if (selectedDay == DayType.REST_DAY) {
                            selectedItemColor
                        } else {
                            unselectedItemColor
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
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
    dayName: String,
    onDayName: (String) -> Unit
) {
    Column {
        Text(
            text = "DAY NAME",
            color = primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = dayName,
            onValueChange = onDayName,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "e.g. Chest",
                    color = secondaryTextColor
                )
            },
            textStyle = LocalTextStyle.current.copy(
                color = primaryTextColor,
                fontSize = 16.sp
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.List,
                    contentDescription = "List"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor
            )
        )
    }
}

@Composable
private fun TipSection(
    textColor: Color,
    borderColor: Color,
    iconColor: Color
) {
    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "You can reorder days later by dragging them.",
                color = textColor,
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
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            containerColor = buttonColor,
            contentColor = textColor,
            disabledContainerColor = buttonColor,
            disabledContentColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text(
            text = "Add day",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}