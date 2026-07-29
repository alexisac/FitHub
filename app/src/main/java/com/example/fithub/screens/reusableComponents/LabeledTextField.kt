package com.example.fithub.screens.reusableComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    iconContentDescription: String?,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    iconColor: Color,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxCharacters: Int = 0
) {
    Column {
        Text(
            text = title,
            color = primaryTextColor,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth()
                .height( if (singleLine)
                        76.dp
                    else
                        120.dp
                ),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = secondaryTextColor
                )
            },
            textStyle = LocalTextStyle.current.copy(
                color = primaryTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            ),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = iconContentDescription,
                    tint = iconColor
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

        if (maxCharacters > 0) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${value.length}/$maxCharacters",
                color = secondaryTextColor,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}