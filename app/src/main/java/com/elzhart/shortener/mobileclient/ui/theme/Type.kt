package com.elzhart.shortener.mobileclient.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.elzhart.shortener.mobileclient.R


val alumni = FontFamily(
    Font(R.font.alumni_sans_regular, FontWeight.Normal),
    Font(R.font.alumni_sans_bold, FontWeight.Bold)
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = alumni,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = alumni,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    labelMedium = TextStyle(
        fontFamily = alumni,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = alumni,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

