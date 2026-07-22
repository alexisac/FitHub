package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.screens.reusableComponents.DatePicker
import com.example.fithub.screens.reusableComponents.TimePicker
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WeightViewModel
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AddWeightScreen (
    viewModel: WeightViewModel,
    isDarkTheme: Boolean,
    onBack: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    LaunchedEffect(uiState.successMessage) {
        if(uiState.successMessage != null) {
            viewModel.clearMessage()
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp)
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

        Spacer(modifier = Modifier.height(24.dp))

        DateAndTimeSection(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.primaryBorderColor,
            iconColor = colors.icon,
            selectedDate = selectedDate,
            selectedTime = selectedTime,
            onDateSelected = { date ->
                selectedDate = date.toString()
            },
            onTimeSelected = { time ->
                selectedTime = time
                    .withSecond(0)
                    .withNano(0)
                    .toString()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        WeightSection(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.primaryBorderColor,
            iconColor = colors.icon,
            weight = weight,
            onWeightSelected = { selectedWeight ->
                weight = selectedWeight
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TipSection(
            informationColor = colors.informationColor,
            borderColor = colors.primaryBorderColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        AddWeightButton(
            primaryTextColor = colors.primaryText,
            buttonColor = colors.icon,
            iconColor = colors.primaryText,
            onClick = {
                viewModel.addWeight(
                    weight = weight,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime
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
    Column {
        OutlinedIconButton(
            onClick = onBack,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, borderColor),
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = iconColor)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = ScreenMessages.BACK_DESCRIPTION
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Column {
            Text(
                text = ScreenMessages.ADD_WEIGHT_TITLE,
                color = primaryTextColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = ScreenMessages.RECORD_WEIGHT_SUBTITLE,
                color = secondaryTextColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun DateAndTimeSection(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    iconColor: Color,
    selectedDate: String,
    selectedTime: String,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit
){
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Column {
        Text(
            text = ScreenMessages.DATE_AND_TIME_TITLE,
            color = primaryTextColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
        ) {
            OutlinedButton(
                onClick = {
                    showDatePicker = true
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 0.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 0.dp
                ),
                contentPadding = PaddingValues(horizontal = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = ScreenMessages.CALENDAR_DESCRIPTION,
                        tint = iconColor,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = ScreenMessages.DATE_TITLE,
                            color = secondaryTextColor,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = selectedDate.ifBlank { ScreenMessages.SELECT_DATE_PLACEHOLDER },
                            color = primaryTextColor,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = {
                    showTimePicker = true
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 16.dp
                ),
                contentPadding = PaddingValues(horizontal = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WatchLater,
                        contentDescription = ScreenMessages.WATCH_DESCRIPTION,
                        tint = iconColor,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = ScreenMessages.TIME_TITLE,
                            color = secondaryTextColor,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = selectedTime.ifBlank { ScreenMessages.SELECT_TIME_PLACEHOLDER },
                            color = primaryTextColor,
                            fontSize = 16.sp
                        )
                    }
                }
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

    TimePicker(
        showDialog = showTimePicker,
        onDismiss = {
            showTimePicker = false
        },
        onTimeSelected = { time ->
            onTimeSelected(time)
            showTimePicker = false
        }
    )
}

@Composable
private fun WeightSection(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    iconColor: Color,
    weight: String,
    onWeightSelected: (String) -> Unit
) {
    Column {
        Text(
            text = ScreenMessages.WEIGHT_TITLE,
            color = primaryTextColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.FitnessCenter,
                    contentDescription = ScreenMessages.WEIGHT_DESCRIPTION,
                    tint = iconColor,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = ScreenMessages.WEIGHT_TITLE,
                        color = secondaryTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    BasicTextField(
                        value = weight,
                        onValueChange = onWeightSelected,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            color = primaryTextColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        cursorBrush = SolidColor(iconColor)
                    )
                }

                Text(
                    text = ScreenMessages.KG,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun TipSection(
    informationColor: Color,
    borderColor: Color
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        shape = RoundedCornerShape(16.dp)
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
                tint = informationColor,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = ScreenMessages.WEIGHT_TIP,
                color = informationColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun AddWeightButton(
    primaryTextColor: Color,
    buttonColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.height(88.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            containerColor = buttonColor,
            contentColor = buttonColor,
            disabledContainerColor = buttonColor,
            disabledContentColor = buttonColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = ScreenMessages.ADD_DESCRIPTION,
                modifier = Modifier.size(30.dp),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = ScreenMessages.ADD_WEIGHT_BUTTON,
                color = primaryTextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}