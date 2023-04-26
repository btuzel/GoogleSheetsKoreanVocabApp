package com.example.googlesheetskoreanvocabapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import timber.log.Timber

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 19.sp,
        lineHeight = 31.sp,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.21.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 21.sp,
        lineHeight = 32.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.28.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 25.8.sp,
        letterSpacing = 0.21.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 34.4.sp,
        letterSpacing = 0.28.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.21.sp,
        color = DarkGray
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    )
    */
)

val Typography.headingMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 33.sp
    )

val Typography.headingRegular: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 33.sp
    )

val Typography.subheadingRegular: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 27.sp
    )

val Typography.bodyBigLight: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        lineHeight = 32.sp
    )

val Typography.bodyMedium: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 25.sp
    )

val Typography.bodyRegular: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 25.sp
    )

val Typography.bodyLight: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        lineHeight = 25.sp
    )


@Composable
fun TextStyle.paragraphSpacing(): Dp {
    return when (this) {
        MaterialTheme.typography.headingRegular, MaterialTheme.typography.headingMedium, MaterialTheme.typography.bodyBigLight -> {
            15.5.dp
        }

        MaterialTheme.typography.bodyRegular, MaterialTheme.typography.bodyMedium, MaterialTheme.typography.bodyLight -> {
            14.25.dp
        }

        else -> {
            Timber.e("Unknown style. Use zero paragraph spacing")
            0.dp
        }
    }
}
