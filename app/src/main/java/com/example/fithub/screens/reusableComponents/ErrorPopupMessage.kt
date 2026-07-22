package com.example.fithub.screens.reusableComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
                    color = colors.error
                )
            } else {
                Text(
                    text = ScreenMessages.SUCCESS_TITLE,
                    color = colors.success
                )
            }
        },
        text = {
            Text(
                text = message,
                color = colors.primaryText
            )
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(
                    text = ScreenMessages.OK,
                    color = colors.primaryText
                )
            }
        }
    )
}