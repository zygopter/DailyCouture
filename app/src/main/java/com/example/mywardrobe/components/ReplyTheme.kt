package com.example.mywardrobe.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReplyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BeigeColors,
        typography = MyTypography,
        shapes = MyShapes
    ) {
        content()
    }
}

val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF8D6E63),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD7CCC8),
    onPrimaryContainer = Color(0xFF5D4037),
    secondary = Color(0xFFA1887F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD7CCC8),
    onSecondaryContainer = Color(0xFF5D4037),
    tertiary = Color(0xFFAED581),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDCEDC8),
    onTertiaryContainer = Color(0xFF33691E),
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9BDBB),
    onErrorContainer = Color(0xFFB71C1C),
    background = Color(0xFFBDBDBD),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFBDBDBD),
    onSurface = Color(0xFF000000),
    surfaceVariant = Color(0xFFD7CCC8),
    onSurfaceVariant = Color(0xFF5D4037),
    // ... et autres couleurs comme outline, inverseOnSurface, etc.
)