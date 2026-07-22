package com.example.fithub.screens.reusableComponents

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    if (!showDialog) {
        return
    }

    val minDate = LocalDate.of(Constants.MIN_YEAR, 1, 1)
    val maxDate = LocalDate.now()

    val minDateMillis = minDate
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

    val maxDateMillis = maxDate
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = maxDateMillis,
        yearRange = Constants.MIN_YEAR..maxDate.year,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in minDateMillis..maxDateMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year in Constants.MIN_YEAR..maxDate.year
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                       val selectedDate = Instant
                           .ofEpochMilli(selectedDateMillis)
                           .atZone(ZoneOffset.UTC)
                           .toLocalDate()

                        onDateSelected(selectedDate)
                    }
                    onDismiss()
                }
            ) {
                Text(ScreenMessages.OK)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(ScreenMessages.CANCEL)
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}