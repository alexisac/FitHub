package com.example.fithub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = FitPrimary,
    secondary = FitSecondary,
    tertiary = FitAccent,

    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCard,

    onPrimary = DarkPrimaryText,
    onSecondary = DarkPrimaryText,
    onTertiary = DarkPrimaryText,
    onBackground = DarkPrimaryText,
    onSurface = DarkPrimaryText,
    onSurfaceVariant = DarkSecondaryText,

    outline = DarkBorder,
    error = ErrorColor
)

private val LightColorScheme = lightColorScheme(
    primary = FitPrimary,
    secondary = FitSecondary,
    tertiary = FitAccent,

    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightCard,

    onPrimary = LightBorder,
    onSecondary = LightPrimaryText,
    onTertiary = LightPrimaryText,
    onBackground = LightPrimaryText,
    onSurface = LightPrimaryText,
    onSurfaceVariant = LightSecondaryText,

    outline = LightBorder,
    error = ErrorColor
)

@Composable
fun FitHubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}