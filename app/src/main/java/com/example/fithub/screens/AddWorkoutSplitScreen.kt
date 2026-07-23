package com.example.fithub.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.DayType
import com.example.fithub.models.WorkoutSplitDay
import com.example.fithub.screens.reusableComponents.DatePicker
import com.example.fithub.screens.reusableComponents.ErrorPopupMessage
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WorkoutViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddWorkoutSplitScreen(
    workoutViewModel: WorkoutViewModel,
    isDarkTheme: Boolean,
    goToAddWorkoutDay: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            workoutViewModel.clearMessages()
            workoutViewModel.clearSplitDraft()
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            onBack = {
                workoutViewModel.clearSplitDraft()
                onBack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SplitName(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            iconColor = colors.primary,
            borderColor = colors.border,
            containerColor = colors.card,
            name = uiState.splitName,
            splitName = { name ->
                workoutViewModel.updateSplitName(name)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        StartDate(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            primaryIconColor = colors.primary,
            secondaryIconColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            selectedDate = uiState.selectedSplitDate,
            onDateSelected = { date ->
                workoutViewModel.updateSelectedSplitDate(
                    date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER))
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ReorderableWorkoutDaysList(
            days = uiState.splitDaysList,
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            draggingContainerColor = colors.selectedContainer,
            workoutColor = colors.primary,
            restDayColor = colors.secondaryText,
            circleTextColor = colors.onPrimary,
            onMove = workoutViewModel::moveWorkoutDay
        )

        Spacer(modifier = Modifier.height(24.dp))

        AddDayButton(
            textColor = colors.primary,
            borderColor = colors.primary,
            containerColor = colors.card,
            onClick = {
                goToAddWorkoutDay()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CreateSplitButton(
            textColor = colors.onPrimary,
            buttonColor = colors.primary,
            onClick = {
                workoutViewModel.createSplit(
                    splitName = uiState.splitName,
                    selectedDate = uiState.selectedSplitDate
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

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = ScreenMessages.ADD_WORKOUT_SPLIT_TITLE,
            color = primaryTextColor,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = ScreenMessages.ADD_WORKOUT_SPLIT_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun SplitName(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    iconColor: Color,
    borderColor: Color,
    containerColor: Color,
    name: String,
    splitName: (String) -> Unit
) {
    Column {
        Text(
            text = ScreenMessages.SPLIT_NAME_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = splitName,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp),
            shape = RoundedCornerShape(20.dp),
            textStyle = LocalTextStyle.current.copy(
                color = primaryTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            ),
            placeholder = {
                Text(
                    text = ScreenMessages.SPLIT_NAME_PLACEHOLDER,
                    color = secondaryTextColor,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.FitnessCenter,
                    contentDescription = ScreenMessages.WEIGHT_DESCRIPTION,
                    tint = iconColor,

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
private fun StartDate(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    primaryIconColor: Color,
    secondaryIconColor: Color,
    borderColor: Color,
    containerColor: Color,
    selectedDate: String,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column {
        Text(
            text = ScreenMessages.START_DATE_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = containerColor,
                contentColor = primaryTextColor
            ),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            ),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = ScreenMessages.CALENDAR_DESCRIPTION,
                    tint = primaryIconColor
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = selectedDate.ifBlank { ScreenMessages.SELECT_DATE_PLACEHOLDER },
                    color = if (selectedDate.isBlank()) {
                        secondaryTextColor
                    } else {
                        primaryTextColor
                    },
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = ScreenMessages.ARROW_DESCRIPTION,
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
private fun ReorderableWorkoutDaysList(
    days: List<WorkoutSplitDay>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    draggingContainerColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    circleTextColor: Color,
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
            text = ScreenMessages.DAYS_IN_SPLIT_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = ScreenMessages.DAYS_IN_SPLIT_SUBTITLE,
            color = secondaryTextColor,
            fontSize = 14.sp
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (days.isEmpty()) {
                item {
                    WorkoutDayItem(
                        day = WorkoutSplitDay(
                            id = -1,
                            position = 1,
                            name = ScreenMessages.WORKOUT_DAY_PLACEHOLDER,
                            day = DayType.WORKOUT
                        ),
                        primaryTextColor = primaryTextColor,
                        secondaryTextColor = secondaryTextColor,
                        borderColor = borderColor,
                        containerColor = containerColor,
                        draggingContainerColor = draggingContainerColor,
                        workoutColor = workoutColor,
                        restDayColor = restDayColor,
                        circleTextColor = circleTextColor,
                        isDragging = false,
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
                            primaryTextColor = primaryTextColor,
                            secondaryTextColor = secondaryTextColor,
                            borderColor = borderColor,
                            containerColor = containerColor,
                            draggingContainerColor = draggingContainerColor,
                            workoutColor = workoutColor,
                            restDayColor = restDayColor,
                            circleTextColor = circleTextColor,
                            isDragging = isDragging,
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
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    draggingContainerColor: Color,
    workoutColor: Color,
    restDayColor: Color,
    circleTextColor: Color,
    isDragging: Boolean,
    dragModifier: Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isDragging) 1.04f else 1f
    )
    val animatedContainerColor by animateColorAsState(
        targetValue = if (isDragging) {
            draggingContainerColor
        } else {
            containerColor
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = animatedContainerColor,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isDragging) {
                    workoutColor
                } else {
                    borderColor
                },
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.DragIndicator,
            contentDescription = ScreenMessages.REORDER_DESCRIPTION,
            tint = if (isDragging) {
                workoutColor
            } else {
                secondaryTextColor
            },
            modifier = dragModifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .size(42.dp)
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
                color = circleTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = day.name,
                color = primaryTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = day.day.toString(),
                color = secondaryTextColor,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun AddDayButton(
    textColor: Color,
    borderColor: Color,
    containerColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = textColor
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = ScreenMessages.ADD_DESCRIPTION,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = ScreenMessages.ADD_DAY_TITLE,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
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
            text = ScreenMessages.CREATE_SPLIT_BUTTON,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}