package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.DragHandle
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.screens.reusableComponents.DatePicker
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.time.LocalDate

@Composable
fun AddWorkoutSplitScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    goToAddWorkoutDay: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    var splitName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }

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

        SplitName(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            iconColor = colors.secondaryText,
            borderColor = colors.secondaryText,
            name = splitName,
            splitName = { selectedSplitName ->
                splitName = selectedSplitName
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        StartDate(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            primaryIconColor = colors.icon,
            secondaryIconColor = colors.secondaryText,
            borderColor = colors.secondaryText,
            selectedDate = selectedDate,
            onDateSelected = { date ->
                selectedDate = date.toString()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReorderableWorkoutDaysList(
            days = uiState.splitDaysList,
            textColor = colors.primaryText,
            borderColor = colors.primaryBorderColor,
            workoutColor = colors.icon,
            restDayColor = colors.secondaryText,
            onMove = workoutViewModel::moveWorkoutDay
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddDayButton(
            buttonContentColor = colors.icon,
            onClick = {
                goToAddWorkoutDay()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CreateSplitButton(
            textColor = colors.primaryText,
            buttonColor = colors.icon,
            onClick = {
                workoutViewModel.createSplit(
                    splitName = splitName,
                    selectedDate = selectedDate
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
                text = "Add workout split",
                color = primaryTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Create a new split and define your training days.",
                color = secondaryTextColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SplitName(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    iconColor: Color,
    borderColor: Color,
    name: String,
    splitName: (String) -> Unit
) {
    Column {
        Text(
            text = "SPLIT NAME",
            color = primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = name,
            onValueChange = splitName,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(
                color = primaryTextColor,
                fontSize = 16.sp
            ),
            placeholder = {
                Text(
                    text = "e.g. Push Pull Legs",
                    color = secondaryTextColor,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.FitnessCenter,
                    contentDescription = "Weight",
                    tint = iconColor,

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
private fun StartDate(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    primaryIconColor: Color,
    secondaryIconColor: Color,
    borderColor: Color,
    selectedDate: String,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "START DATE",
            color = primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedButton(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            ),
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 0.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = "Calendar",
                    tint = primaryIconColor
                )

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = selectedDate.ifBlank { "Select date" },
                        color = secondaryTextColor,
                        fontSize = 16.sp
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = "Arrow",
                    tint = secondaryIconColor
                )
            }
        }
    }

    DatePicker (
        showDialog = showDatePicker,
        onDismiss = {
            showDatePicker = false
        },
        onDateSelected = { date ->
            onDateSelected(date)
            showDatePicker = false
        }
    )
}

@Composable
private fun AddDayButton(
    buttonContentColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(60.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = buttonContentColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                tint = buttonContentColor
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Add day",
                color = buttonContentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ReorderableWorkoutDaysList(
    days: List<WorkoutSplitDay>,
    textColor: Color,
    borderColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    onMove: (fromIndex: Int, toIndex: Int) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val reorderableState = rememberReorderableLazyListState(
        lazyListState = lazyListState
    ) { from, to ->
        onMove(
            from.index,
            to.index
        )
    }

    Column {
        Text(
            text = "DAYS IN SPLIT",
            color = textColor
        )

        Text(
            text = "Add the days that make up this split.",
            color = textColor
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (days.isEmpty()) {
                item {
                    WorkoutDayItem(
                        day = WorkoutSplitDay(
                            id = -1,
                            position = 1,
                            name = "e.g. workout day",
                            day = DayType.WORKOUT
                        ),
                        textColor = textColor,
                        borderColor = borderColor,
                        isDragging = false,
                        workoutColor = workoutColor,
                        restDayColor = restDayColor,
                        dragModifier = Modifier
                    )
                }
            } else {
                items(
                    items = days,
                    key = { day -> day.id }
                ) { day ->
                    ReorderableItem(
                        state = reorderableState,
                        key = day.id
                    ) { isDragging ->
                        WorkoutDayItem(
                            day = day,
                            textColor = textColor,
                            borderColor = borderColor,
                            isDragging = isDragging,
                            workoutColor = workoutColor,
                            restDayColor = restDayColor,
                            dragModifier = Modifier.longPressDraggableHandle()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutDayItem(
    day: WorkoutSplitDay,
    textColor: Color,
    borderColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    isDragging: Boolean,
    dragModifier: Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.DragIndicator,
            contentDescription = "Reorder",
            tint = textColor,
            modifier = dragModifier
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (day.day == DayType.WORKOUT) {
                        workoutColor
                    } else {
                        restDayColor
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.position.toString(),
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = day.name,
                color = textColor
            )

            Text(
                text = day.day.toString(),
                color = textColor
            )
        }
    }
}

@Composable
private fun CreateSplitButton(
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
            text = "Create split",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}