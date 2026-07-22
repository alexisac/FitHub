package com.example.fithub.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.fithub.models.AppThemeColors

//Brand colors
val FitPrimary = Color(0xFF53A687)
val FitSecondary = Color(0xFFB2C84F)
val FitAccent = Color(0xFFD2E878)

// Dark theme
val DarkBackground = Color(0xFF0B1B17)
val DarkSurface = Color(0xFF11261F)
val DarkCard = Color(0xFF18342D)

val DarkPrimaryText = Color(0xFFEAF2EE)
val DarkSecondaryText = Color(0xFFA3B8AE)

val DarkBorder = Color(0xFF27463D)

val DarkSelectedContainer = Color(0xFF1F3D34)
val DarkInfoContainer = Color(0xFF264A3F)

// Light theme
val LightBackground = Color(0xFFE8FAF4)
val LightSurface = Color(0xFFFFFFFF)
val LightCard = Color(0xFFF2FAF7)

val LightPrimaryText = Color(0xFF14323A)
val LightSecondaryText = Color(0xFF6E8781)

val LightBorder = Color(0xFFB9D8CD)

val LightSelectedContainer = Color(0xFFEDF8F4)
val LightInfoContainer = Color(0xFFF5F9E9)

// Status colors
val ErrorColor = Color(0xFFE66B6B)
val SuccessColor = Color(0xFF53A687)

object AppColors {
    fun colors(isDarkTheme: Boolean): AppThemeColors {
        return if (isDarkTheme) {
            AppThemeColors(
                background = DarkBackground,
                surface = DarkSurface,
                card = DarkCard,
                selectedContainer = DarkSelectedContainer,
                infoContainer = DarkInfoContainer,
                onPrimary = DarkPrimaryText,

                primaryText = DarkPrimaryText,
                secondaryText = DarkSecondaryText,

                primary = FitPrimary,
                secondary = FitSecondary,
                accent = FitAccent,

                border = DarkBorder,
                icon = FitPrimary,
                information = FitSecondary,

                track = DarkCard,
                thumb = FitPrimary,
                inactiveIcon = DarkSecondaryText,

                error = ErrorColor,
                success = SuccessColor,

                primaryBorderColor = ErrorColor,
                secondaryBorderColor = ErrorColor
            )
        } else {
            AppThemeColors(
                background = LightBackground,
                surface = LightSurface,
                card = LightCard,
                selectedContainer = LightSelectedContainer,
                infoContainer = LightInfoContainer,
                onPrimary = Color.White,

                primaryText = LightPrimaryText,
                secondaryText = LightSecondaryText,

                primary = FitPrimary,
                secondary = FitSecondary,
                accent = FitAccent,

                border = LightBorder,
                icon = FitPrimary,
                information = FitSecondary,

                track = LightCard,
                thumb = FitPrimary,
                inactiveIcon = LightSecondaryText,

                error = ErrorColor,
                success = SuccessColor,

                primaryBorderColor = ErrorColor,
                secondaryBorderColor = ErrorColor
            )
        }
    }
}