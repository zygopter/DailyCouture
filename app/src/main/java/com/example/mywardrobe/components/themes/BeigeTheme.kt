package com.example.mywardrobe.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BeigeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BeigeColors,
        typography = MyTypography,
        shapes = MyShapes
    ) {
        content()
    }
}

val BeigeColors = lightColorScheme(
    primary = Color(0xFFB06660),
    onPrimary = Color(0xFFFFFFFF), // Choisissez une couleur qui contraste bien avec le `primary`
    primaryContainer = Color(0xFFD9A88F),
    onPrimaryContainer = Color(0xFF000000), // Idem pour le contraste
    secondary = Color(0xFFEAC3B9),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFCA8F42),
    onSecondaryContainer = Color(0xFFFFFFFF),
    background = Color(0xFFAB9C73),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF9BAF8E),
    onSurface = Color(0xFF000000)
)

val MyTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    // Définissez d'autres styles typographiques selon vos besoins
)

val MyShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)
