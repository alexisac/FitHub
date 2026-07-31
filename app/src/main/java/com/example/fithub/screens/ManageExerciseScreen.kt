package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.Exercise
import com.example.fithub.screens.reusableComponents.Header
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.ExerciseViewModel

@Composable
fun ManageExerciseScreen(
    exerciseViewModel: ExerciseViewModel,
    isDarkTheme: Boolean,
    goToAddExercise: () -> Unit,
    goToEditExercise: (Long) -> Unit,
    onBack: () -> Unit
) {
    val uiState by exerciseViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }
    var exerciseToDelete by remember { mutableStateOf<Exercise?>(null) }

    LaunchedEffect(Unit) {
        exerciseViewModel.getAllExercises()
    }

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

        ExerciseList(
            exercises = uiState.exercisesList,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            deleteColor = colors.primary,
            onExerciseClick = { exercise ->
                if (exercise.description.isNotBlank()) {
                    selectedExercise = exercise
                }
            },
            onEditClick = { exercise ->
                goToEditExercise(exercise.id)
            },
            onDeleteClick = { exercise ->
                exerciseToDelete = exercise
            }
        )

        selectedExercise?.let { exercise ->
            ExerciseDescriptionDialog(
                exercise = exercise,
                primaryTextColor = colors.primaryText,
                secondaryTextColor = colors.secondaryText,
                containerColor = colors.card,
                confirmColor = colors.primary,
                onDismiss = {
                    selectedExercise = null
                }
            )
        }

        exerciseToDelete?.let { exercise ->
            DeleteExerciseDialog(
                exerciseName = exercise.name,
                primaryTextColor = colors.primaryText,
                secondaryTextColor = colors.secondaryText,
                containerColor = colors.card,
                confirmColor = colors.error,
                onConfirm = {
                    exerciseViewModel.deleteExercise(exercise.id)
                    exerciseToDelete = null
                },
                onDismiss = {
                    exerciseToDelete = null
                }
            )
        }
    }
}

@Composable
private fun ExerciseList(
    exercises: List<Exercise>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    deleteColor: Color,
    onExerciseClick: (Exercise) -> Unit,
    onEditClick: (Exercise) -> Unit,
    onDeleteClick: (Exercise) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(exercises) { exercise ->
            ExerciseCard(
                exercise = exercise,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                containerColor = containerColor,
                iconColor = iconColor,
                deleteColor = deleteColor,
                onClick = {
                    onExerciseClick(exercise)
                },
                onEditClick = {
                    onEditClick(exercise)
                },
                onDeleteClick = {
                    onDeleteClick(exercise)
                }
            )
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    deleteColor: Color,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            },
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
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = exercise.name,
                        color = primaryTextColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = exercise.muscleGroup.name,
                        color = secondaryTextColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = onEditClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = ScreenMessages.EDIT_DESCRIPTION,
                        tint = iconColor
                    )
                }

                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = ScreenMessages.DELETE_DESCRIPTION,
                        tint = deleteColor
                    )
                }
            }

            if (exercise.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = exercise.description,
                    color = secondaryTextColor,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ExerciseDescriptionDialog(
    exercise: Exercise,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    containerColor: Color,
    confirmColor: Color,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = containerColor,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = exercise.name,
                color = primaryTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = exercise.description,
                color = secondaryTextColor,
                fontSize = 16.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = ScreenMessages.OK,
                    color = confirmColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
private fun DeleteExerciseDialog(
    exerciseName: String,
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
                text = ScreenMessages.DELETE_EXERCISE_TITLE,
                color = primaryTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = ScreenMessages.deleteExerciseMessage(exerciseName),
                color = secondaryTextColor,
                fontSize = 16.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = ScreenMessages.DELETE_DESCRIPTION,
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
                    text = ScreenMessages.CANCEL,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}