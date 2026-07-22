package com.example.fithub.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.fithub.models.AppThemeColors

// General dark theme
val DarkBackground = Color(0xFF0B0F17)
val DarkSurface = Color(0xFF111827)
val DarkCard = Color(0xFF151B26)
val TextPrimaryDark = Color(0xFFF8FAFC)
val TextSecondaryDark = Color(0xFFAAB2C0)
val BorderDark = Color(0xFF313A49)

// General light theme
val LightBackground = Color(0xFFF8FAFF)
val LightSurface = Color(0xFFFFFFFF)
val LightCard = Color(0xFFF1F5FF)
val TextPrimaryLight = Color(0xFF111827)
val TextSecondaryLight = Color(0xFF667085)
val BorderLight = Color(0xFFD7DEEA)

// Common colors
val PrimaryBlueDark = Color(0xFF3F6DFF)
val PrimaryBlueLight = Color(0xFF2F63F6)
val SoftBlueDark = Color(0xFFAFC6FF)
val SoftBlueLight = Color(0xFFE8EEFF)
val PurpleAccent = Color(0xFF6D4CFF)
val ErrorRed = Color(0xFFFF4D4F)
val SuccessGreen = Color(0xFF4BB543)

// Main colors
val MainRed = Color(0xFFF04770)
val MainOrange = Color(0xFFF78C6A)
val MainYellow = Color(0xFFFFD167)
val MainGreen = Color(0xFF06D7A0)
val MainLightBlue = Color(0xFF108AB1)
val MainDarkBlue = Color(0xFF073A4B)

object AppColors {
    fun colors(isDarkTheme: Boolean): AppThemeColors {
        return if (isDarkTheme) {
            AppThemeColors(
                primaryText = TextPrimaryDark,
                secondaryText = TextSecondaryDark,
                primaryBorderColor = TextPrimaryDark,
                secondaryBorderColor = MainRed,
                informationColor = MainGreen,
                icon = MainRed,
                error = ErrorRed,
                success = SuccessGreen
            )
        } else {
            AppThemeColors(
                primaryText = TextPrimaryLight,
                secondaryText = TextSecondaryLight,
                primaryBorderColor = TextPrimaryLight,
                secondaryBorderColor = MainRed,
                informationColor = MainGreen,
                icon = MainRed,
                error = ErrorRed,
                success = SuccessGreen
            )
        }
    }
}