package com.example.compose

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.elzhart.shortener.mobileclient.ui.theme.Shapes
import com.elzhart.shortener.mobileclient.ui.theme.Typography
import com.elzhart.shortener.mobileclient.ui.theme.backgroundDark
import com.elzhart.shortener.mobileclient.ui.theme.backgroundLight
import com.elzhart.shortener.mobileclient.ui.theme.errorContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.errorContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.errorDark
import com.elzhart.shortener.mobileclient.ui.theme.errorLight
import com.elzhart.shortener.mobileclient.ui.theme.inverseOnSurfaceDark
import com.elzhart.shortener.mobileclient.ui.theme.inverseOnSurfaceLight
import com.elzhart.shortener.mobileclient.ui.theme.inversePrimaryDark
import com.elzhart.shortener.mobileclient.ui.theme.inversePrimaryLight
import com.elzhart.shortener.mobileclient.ui.theme.inverseSurfaceDark
import com.elzhart.shortener.mobileclient.ui.theme.inverseSurfaceLight
import com.elzhart.shortener.mobileclient.ui.theme.onBackgroundDark
import com.elzhart.shortener.mobileclient.ui.theme.onBackgroundLight
import com.elzhart.shortener.mobileclient.ui.theme.onErrorContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.onErrorContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.onErrorDark
import com.elzhart.shortener.mobileclient.ui.theme.onErrorLight
import com.elzhart.shortener.mobileclient.ui.theme.onPrimaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.onPrimaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.onPrimaryDark
import com.elzhart.shortener.mobileclient.ui.theme.onPrimaryLight
import com.elzhart.shortener.mobileclient.ui.theme.onSecondaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.onSecondaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.onSecondaryDark
import com.elzhart.shortener.mobileclient.ui.theme.onSecondaryLight
import com.elzhart.shortener.mobileclient.ui.theme.onSurfaceDark
import com.elzhart.shortener.mobileclient.ui.theme.onSurfaceLight
import com.elzhart.shortener.mobileclient.ui.theme.onSurfaceVariantDark
import com.elzhart.shortener.mobileclient.ui.theme.onSurfaceVariantLight
import com.elzhart.shortener.mobileclient.ui.theme.onTertiaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.onTertiaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.onTertiaryDark
import com.elzhart.shortener.mobileclient.ui.theme.onTertiaryLight
import com.elzhart.shortener.mobileclient.ui.theme.outlineDark
import com.elzhart.shortener.mobileclient.ui.theme.outlineLight
import com.elzhart.shortener.mobileclient.ui.theme.outlineVariantDark
import com.elzhart.shortener.mobileclient.ui.theme.outlineVariantLight
import com.elzhart.shortener.mobileclient.ui.theme.primaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.primaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.primaryDark
import com.elzhart.shortener.mobileclient.ui.theme.primaryLight
import com.elzhart.shortener.mobileclient.ui.theme.scrimDark
import com.elzhart.shortener.mobileclient.ui.theme.scrimLight
import com.elzhart.shortener.mobileclient.ui.theme.secondaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.secondaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.secondaryDark
import com.elzhart.shortener.mobileclient.ui.theme.secondaryLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceBrightDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceBrightLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerHighDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerHighLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerHighestDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerHighestLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerLowDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerLowLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerLowestDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceContainerLowestLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceDimDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceDimLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceLight
import com.elzhart.shortener.mobileclient.ui.theme.surfaceVariantDark
import com.elzhart.shortener.mobileclient.ui.theme.surfaceVariantLight
import com.elzhart.shortener.mobileclient.ui.theme.tertiaryContainerDark
import com.elzhart.shortener.mobileclient.ui.theme.tertiaryContainerLight
import com.elzhart.shortener.mobileclient.ui.theme.tertiaryDark
import com.elzhart.shortener.mobileclient.ui.theme.tertiaryLight

@Immutable
data class ExtendedColorScheme(
    val customColor1: ColorFamily,
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun LinkShortenerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            setUpEdgeToEdge(view, darkTheme)
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
        // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
        else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
    }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
}

