package com.example.fithub.screens.reusableComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.ui.theme.AppColors

@Composable
fun ErrorPopupMessage(
    message: String,
    isErrorMessage: Boolean,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit
) {
    val colors = AppColors.colors(isDarkTheme)

    AlertDialog(
        containerColor = colors.card,
        shape = RoundedCornerShape(20.dp),
        iconContentColor = if (isErrorMessage) {
            colors.error
        } else {
            colors.success
        },
        titleContentColor = colors.primaryText,
        textContentColor = colors.secondaryText,
        icon = {
            if (isErrorMessage) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = ScreenMessages.ERROR_DESCRIPTION,
                    tint = colors.error
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = ScreenMessages.SUCCESS_DESCRIPTION,
                    tint = colors.success
                )
            }
        },
        title = {
            if (isErrorMessage) {
                Text(
                    text = ScreenMessages.ERROR_TITLE,
                    color = colors.primaryText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = ScreenMessages.SUCCESS_TITLE,
                    color = colors.primaryText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Text(
                text = message,
                color = colors.primaryText,
                fontSize = 16.sp
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = colors.primary
                )
            ) {
                Text(
                    text = ScreenMessages.OK,
                    color = colors.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}