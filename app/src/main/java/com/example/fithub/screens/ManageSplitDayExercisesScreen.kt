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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.Exercise
import com.example.fithub.screens.reusableComponents.ActionButton
import com.example.fithub.screens.reusableComponents.Header
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.ExerciseViewModel
import com.example.fithub.viewModels.WorkoutSplitDayExercisesViewModel
import com.example.fithub.viewModels.WorkoutViewModel

@Composable
fun ManageSplitDayExercisesScreen(
    exerciseViewModel: ExerciseViewModel,
    workoutViewModel: WorkoutViewModel,
    workoutSplitDayExercisesViewModel: WorkoutSplitDayExercisesViewModel,
    workoutSplitDayId: Long,
    isDarkTheme: Boolean,
    onBack: () -> Unit
) {
    val exerciseUiState by exerciseViewModel.uiState.collectAsState()
    val workoutUiState by workoutViewModel.uiState.collectAsState()
    val splitDayExercisesUiState by workoutSplitDayExercisesViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    var selectedExerciseIds by remember { mutableStateOf<Set<Long>>(emptySet()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredExercises = exerciseUiState.exercisesList.filter { exercise ->
        searchQuery.isBlank() || exercise.name.contains(
                    other = searchQuery.trim(),
                    ignoreCase = true
                )
    }

    LaunchedEffect(workoutSplitDayId) {
        workoutViewModel.getSplitDayById(workoutSplitDayId)
        exerciseViewModel.getAllExercises()
    }

    LaunchedEffect(splitDayExercisesUiState.successMessage) {
        if (splitDayExercisesUiState.successMessage != null) {
            workoutSplitDayExercisesViewModel.clearMessages()
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
            title = workoutUiState.splitDay?.name ?: ScreenMessages.MANAGE_SPLIT_DAYS_TITLE,
            subtitle = ScreenMessages.MANAGE_SPLIT_DAY_EXERCISES_SUBTITLE,
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

        ExerciseSearchField(
            value = searchQuery,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onValueChange = { newQuery ->
                searchQuery = newQuery
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ExercisesList(
            exercisesList = filteredExercises,
            selectedExerciseIds = selectedExerciseIds,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            selectedBorderColor = colors.primary,
            containerColor = colors.card,
            selectedContainerColor = colors.selectedContainer,
            selectedIconColor = colors.primary,
            onSelectExercise = { exerciseId ->
                selectedExerciseIds =
                    if (exerciseId in selectedExerciseIds) {
                        selectedExerciseIds - exerciseId
                    } else {
                        selectedExerciseIds + exerciseId
                    }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ActionButton(
            text = ScreenMessages.SAVE_BUTTON,
            textColor = colors.onPrimary,
            containerColor = colors.primary,
            onClick = {
                workoutSplitDayExercisesViewModel.saveExercisesForSplitDay(
                    workoutSplitDayId = workoutSplitDayId,
                    exerciseIds = selectedExerciseIds.toList()
                )
            }
        )
    }
}

@Composable
private fun ExerciseSearchField(
    value: String,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(20.dp),
        placeholder = {
            Text(
                text = ScreenMessages.SEARCH_EXERCISES_PLACEHOLDER,
                color = secondaryTextColor
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = ScreenMessages.SEARCH_DESCRIPTION,
                tint = iconColor
            )
        },
        trailingIcon = {
            if (value.isNotBlank()) {
                IconButton(
                    onClick = {
                        onValueChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = ScreenMessages.CLEAR_DESCRIPTION,
                        tint = secondaryTextColor
                    )
                }
            }
        },
        textStyle = LocalTextStyle.current.copy(
            color = primaryTextColor,
            fontSize = 17.sp
        ),
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

@Composable
private fun ExercisesList(
    exercisesList: List<Exercise>,
    selectedExerciseIds: Set<Long>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    selectedBorderColor: Color,
    containerColor: Color,
    selectedContainerColor: Color,
    selectedIconColor: Color,
    onSelectExercise: (Long) -> Unit
) {
    if (exercisesList.isEmpty()) {
        Text(
            text = ScreenMessages.NO_EXERCISES_FOUND,
            color = secondaryTextColor,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = exercisesList,
            key = { exercise -> exercise.id }
        ) { exercise ->
            ExerciseCard(
                exercise = exercise,
                isSelected = exercise.id in selectedExerciseIds,
                primaryTextColor = primaryTextColor,
                secondaryTextColor = secondaryTextColor,
                borderColor = borderColor,
                selectedBorderColor = selectedBorderColor,
                containerColor = containerColor,
                selectedContainerColor = selectedContainerColor,
                selectedIconColor = selectedIconColor,
                onSelectExercise = {
                    onSelectExercise(exercise.id)
                }
            )
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    isSelected: Boolean,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    selectedBorderColor: Color,
    containerColor: Color,
    selectedContainerColor: Color,
    selectedIconColor: Color,
    onSelectExercise: () -> Unit
) {
    OutlinedCard(
        onClick = onSelectExercise,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = if (isSelected)
                2.dp
            else
                1.dp,
            color = if (isSelected) {
                selectedBorderColor
            } else {
                borderColor
            }
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (isSelected) {
                selectedContainerColor
            } else {
                containerColor
            }
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
                    text = exercise.name,
                    color = if (isSelected) {
                        selectedIconColor
                    } else {
                        primaryTextColor
                    },
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

            if (isSelected) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = ScreenMessages.SELECTED_DESCRIPTION,
                    tint = selectedIconColor
                )
            }
        }
    }
}