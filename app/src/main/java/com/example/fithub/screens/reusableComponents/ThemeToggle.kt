package com.example.fithub.screens.reusableComponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.ui.theme.*

@Composable
fun ThemeToggle(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppColors.colors(isDarkTheme)
    val thumbOffset by animateDpAsState(
        targetValue = if (isDarkTheme) 40.dp else 0.dp,
        label = ScreenMessages.THEME_TOGGLE_OFFSET_DESCRIPTION
    )

    Box(
        modifier = modifier
            .size(width = 84.dp, height = 44.dp)
            .clip(RoundedCornerShape(50))
            .background(colors.track)
            .border(
                width = 1.dp,
                color = colors.border,
                shape = RoundedCornerShape(50)
            )
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .offset(x = thumbOffset)
                .clip(CircleShape)
                .background(colors.thumb)
        )

        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        onThemeChange(false)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LightMode,
                    contentDescription = ScreenMessages.LIGHT_MODE_DESCRIPTION,
                    tint = if (!isDarkTheme) colors.onPrimary else colors.inactiveIcon,
                    modifier = Modifier.size(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        onThemeChange(true)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = ScreenMessages.DARK_MODE_DESCRIPTION,
                    tint = if (isDarkTheme) colors.onPrimary else colors.inactiveIcon,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}