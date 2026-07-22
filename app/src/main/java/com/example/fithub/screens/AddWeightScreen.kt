package com.example.fithub.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
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
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.screens.reusableComponents.DatePicker
import com.example.fithub.screens.reusableComponents.ErrorPopupMessage
import com.example.fithub.screens.reusableComponents.TimePicker
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WeightViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

        DateAndTimeSection(
            primaryTextColor = colors.primaryText,
            secondaryTextColor = colors.secondaryText,
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            selectedDate = selectedDate,
            selectedTime = selectedTime,
            onDateSelected = { date ->
                selectedDate = date.format(
                    DateTimeFormatter.ofPattern(Constants.DATE_FORMATTER)
                )
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
            borderColor = colors.border,
            containerColor = colors.card,
            iconColor = colors.primary,
            weight = weight,
            onWeightSelected = { selectedWeight ->
                weight = selectedWeight
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TipSection(
            informationColor = colors.secondary,
            borderColor = colors.secondary,
            containerColor = colors.infoContainer
        )

        Spacer(modifier = Modifier.height(24.dp))

        AddWeightButton(
            primaryTextColor = colors.onPrimary,
            buttonColor = colors.primary,
            iconColor = colors.onPrimary,
            onClick = {
                viewModel.addWeight(
                    weight = weight,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime
                )
            }
        )

        uiState.errorMessage?.let { error ->
            ErrorPopupMessage(
                message = error,
                isErrorMessage = true,
                isDarkTheme = isDarkTheme,
                onDismiss = {
                    viewModel.clearMessage()
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
            border = BorderStroke(1.dp, borderColor),
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


        Column {
            Text(
                text = ScreenMessages.ADD_WEIGHT_TITLE,
                color = primaryTextColor,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = ScreenMessages.RECORD_WEIGHT_SUBTITLE,
                color = secondaryTextColor,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun DateAndTimeSection(
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
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
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
        ) {
            OutlinedButton(
                onClick = {
                    showDatePicker = true
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = containerColor,
                    contentColor = primaryTextColor
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

                    Spacer(modifier = Modifier.width(16.dp))

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

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = {
                    showTimePicker = true
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = containerColor,
                    contentColor = primaryTextColor
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

                    Spacer(modifier = Modifier.width(16.dp))

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
    containerColor: Color,
    iconColor: Color,
    weight: String,
    onWeightSelected: (String) -> Unit
) {
    Column {
        Text(
            text = ScreenMessages.WEIGHT_TITLE,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(
                width = 1.dp,
                color = borderColor
            ),
            colors = CardDefaults.outlinedCardColors(
                containerColor = containerColor,
                contentColor = primaryTextColor
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
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
                        onValueChange = { value ->
                            onWeightSelected(value.replace(',', '.'))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            color = primaryTextColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        cursorBrush = SolidColor(iconColor)
                    )
                }

                Text(
                    text = ScreenMessages.KG,
                    color = secondaryTextColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun TipSection(
    informationColor: Color,
    borderColor: Color,
    containerColor: Color
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 76.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor,
            contentColor = informationColor
        )
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
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(20.dp),
        border = null,
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
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}