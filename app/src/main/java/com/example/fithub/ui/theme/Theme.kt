package com.example.fithub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueDark,
    secondary = SoftBlueDark,
    tertiary = PurpleAccent,

    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCard,

    onPrimary = TextPrimaryDark,
    onSecondary = TextPrimaryDark,
    onTertiary = TextPrimaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    onSurfaceVariant = TextSecondaryDark,

    outline = BorderDark,
    error = ErrorRed
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlueLight,
    secondary = SoftBlueLight,
    tertiary = PurpleAccent,

    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightCard,

    onPrimary = TextPrimaryDark,
    onSecondary = TextPrimaryLight,
    onTertiary = TextPrimaryDark,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    onSurfaceVariant = TextSecondaryLight,

    outline = BorderLight,
    error = ErrorRed
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