package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R


private val Markazi = FontFamily(Font(R.font.markazi_text_regular))
private val Karla = FontFamily(Font(R.font.karla_regular))
val Typography = Typography(
    //Title
    titleLarge = TextStyle(
        fontFamily = Markazi,
        fontSize = 64.sp,
        fontWeight = FontWeight.Medium,
    ),
    //SubTitle
    titleMedium = TextStyle(
        fontFamily = Markazi,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
    ),
    //SectionTitle
    titleSmall = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    //Main font
    bodyLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    //Button
    labelLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )

)