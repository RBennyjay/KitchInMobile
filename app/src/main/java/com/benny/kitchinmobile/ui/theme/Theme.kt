package com.benny.kitchinmobile.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = KitchInCoral,
    onPrimary = Color.White,
    secondary = Color(0xFFE2E2E6),
    background = KitchInDark,
    surface = Color(0xFF2B2930)
)

private val LightColorScheme = lightColorScheme(
    primary = KitchInCoral,         // Your Brand Coral
    onPrimary = Color.White,
    secondary = KitchInGrey,
    background = KitchInBone,       // Your Off-White/Bone background
    surface = Color.White,
    onBackground = KitchInDark,
    onSurface = KitchInDark
)

@Composable
fun KitchInMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to prioritize your Coral brand colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}