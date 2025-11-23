package com.fatec.salafacil.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Brand200,
    onPrimary = Black,
    primaryContainer = Brand500,
    onPrimaryContainer = White,

    secondary = Brand300,
    onSecondary = Black,

    background = Grey500,
    onBackground = Grey300,

    surface = Grey400,
    onSurface = Grey300,

    error = ErrorColor,
    onError = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Brand500,
    onPrimary = White,
    primaryContainer = Brand100,
    onPrimaryContainer = Grey500,

    secondary = Brand300,
    onSecondary = White,
    secondaryContainer = Brand100,
    onSecondaryContainer = Grey500,

    tertiary = Brand400,
    onTertiary = White,

    background = Grey100,
    onBackground = Grey500,

    surface = White,
    onSurface = Grey500,

    error = ErrorColor,
    onError = White,
)

@Composable
fun SalaFacilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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