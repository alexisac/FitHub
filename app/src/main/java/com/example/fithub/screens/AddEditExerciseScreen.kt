package com.example.fithub.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.MuscleGroup
import com.example.fithub.screens.reusableComponents.ActionButton
import com.example.fithub.screens.reusableComponents.ErrorPopupMessage
import com.example.fithub.screens.reusableComponents.Header
import com.example.fithub.screens.reusableComponents.LabeledTextField
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.ExerciseViewModel

@Composable
fun AddEditExerciseScreen(
    exerciseViewModel: ExerciseViewModel,
    isDarkTheme: Boolean,
    exerciseId: Long? = null,
    onBack: () -> Unit
) {
    val uiState by exerciseViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)
    val isEditMode = exerciseId != null

    var exerciseName by remember { mutableStateOf("") }
    var selectedMuscleGroup by remember { mutableStateOf(MuscleGroup.CHEST) }
    var exerciseDescription by remember { mutableStateOf("") }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            exerciseViewModel.clearMessage()
            onBack()
        }
    }

    LaunchedEffect(exerciseId) {
        if (exerciseId != null)
            exerciseViewModel.getExerciseById(exerciseId)
    }

    LaunchedEffect(uiState.selectedExercise) {
        uiState.selectedExercise?.let { exercise ->
            exerciseName = exercise.name
            selectedMuscleGroup = exercise.muscleGroup
            exerciseDescription = exercise.description
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp, vertical = 40.dp)
    ) {
        Header(
            title = if (isEditMode) {
                ScreenMessages.EDIT_EXERCISE_TITLE
            } else {
                ScreenMessages.ADD_EXERCISE_TITLE
            },
            subtitle = if (isEditMode) {
                ScreenMessages.EDIT_EXERCISE_SUBTITLE
            } else {
                ScreenMessages.ADD_EXERCISE_SUBTITLE
            },
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onBack = {
                exerciseViewModel.clearMessage()
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        LabeledTextField(
            title = ScreenMessages.EXERCISE_NAME_TITLE,
            value = exerciseName,
            onValueChange = { newExerciseName ->
                exerciseName = newExerciseName
            },
            placeholder = ScreenMessages.BENCH_PRESS_PLACEHOLDER,
            icon = Icons.Outlined.SportsGymnastics,
            iconContentDescription = ScreenMessages.SPORT_DESCRIPTION,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        MuscleGroupSelector(
            selectedMuscleGroup = selectedMuscleGroup,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onMuscleGroupSelected = { muscleGroup ->
                selectedMuscleGroup = muscleGroup
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        LabeledTextField(
            title = ScreenMessages.EXERCISE_DESCRIPTION_TITLE,
            value = exerciseDescription,
            onValueChange = { newDescription ->
                exerciseDescription = newDescription
            },
            placeholder = ScreenMessages.EXERCISE_DESCRIPTION_PLACEHOLDER,
            icon = Icons.Outlined.Description,
            iconContentDescription = ScreenMessages.DESCRIPTION_DESCRIPTION,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            singleLine = false,
            minLines = 3,
            maxLines = 3,
            maxCharacters = 300
        )

        Spacer(modifier = Modifier.height(24.dp))

        ActionButton(
            text = if (isEditMode) {
                ScreenMessages.UPDATE_EXERCISE_BUTTON
            } else {
                ScreenMessages.CREATE_EXERCISE_BUTTON
            },
            textColor = colors.onPrimary,
            containerColor = colors.primary,
            onClick = {
                if (isEditMode) {
                    exerciseViewModel.updateExercise(
                        exerciseId = exerciseId,
                        exerciseName = exerciseName,
                        muscleGroup = selectedMuscleGroup,
                        exerciseDescription = exerciseDescription
                    )
                } else {
                    exerciseViewModel.addExercise(
                        exerciseName = exerciseName,
                        muscleGroup = selectedMuscleGroup,
                        exerciseDescription = exerciseDescription
                    )
                }
            }
        )

        uiState.errorMessage?.let { error ->
            ErrorPopupMessage(
                message = error,
                isErrorMessage = true,
                isDarkTheme = isDarkTheme,
                onDismiss = {
                    exerciseViewModel.clearMessage()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MuscleGroupSelector(
    selectedMuscleGroup: MuscleGroup,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    onMuscleGroupSelected: (MuscleGroup) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = ScreenMessages.MUSCLE_GROUP_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = ScreenMessages.MUSCLE_GROUP_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedMuscleGroup.name,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .menuAnchor(),
                shape = RoundedCornerShape(20.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = primaryTextColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
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

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                containerColor = containerColor
            ) {
                MuscleGroup.entries.forEach { muscleGroup ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = muscleGroup.name,
                                color = if (muscleGroup == selectedMuscleGroup) {
                                    iconColor
                                } else {
                                    primaryTextColor
                                },
                                fontSize = 16.sp,
                                fontWeight = if (muscleGroup == selectedMuscleGroup) {
                                    FontWeight.SemiBold
                                } else {
                                    FontWeight.Normal
                                }
                            )
                        },
                        onClick = {
                            onMuscleGroupSelected(muscleGroup)
                            expanded = false
                        },
                        leadingIcon = {
                            if (muscleGroup == selectedMuscleGroup) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = null,
                                    tint = iconColor
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}