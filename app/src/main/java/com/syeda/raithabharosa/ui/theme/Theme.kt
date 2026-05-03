package com.syeda.raithabharosa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.syeda.raithabharosa.ui.theme.Typography
import com.syeda.raithabharosa.ui.theme.*

// 🌿 CUSTOM COLORS

// 🌙 DARK THEME (optional)
private val DarkColorScheme = darkColorScheme(
    primary = GreenLight,
    secondary = GreenDark,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onBackground = Color.White
)

// ☀️ LIGHT THEME (MAIN)
private val LightColorScheme = lightColorScheme(
    primary = GreenDark,
    secondary = GreenLight,
    background = Cream,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black
)

@Composable
fun RaithaBharosaTheme(
    darkTheme: Boolean = false, // 🔥 force light theme for now
    dynamicColor: Boolean = false, // 🔥 disable system override
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}