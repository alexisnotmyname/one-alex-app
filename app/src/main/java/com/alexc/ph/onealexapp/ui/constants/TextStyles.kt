package com.alexc.ph.onealexapp.ui.constants


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alexc.ph.onealexapp.ui.theme.AppTypography
import com.alexc.ph.onealexapp.ui.theme.TodoItemTextColor
import com.alexc.ph.onealexapp.ui.theme.surfaceVariantDark

val MovieTitleTextStyle = AppTypography.labelLarge.copy(
    color = TodoItemTextColor
)

val MovieTitleHeadlineTextStyle = AppTypography.headlineSmall.copy(
    color = TodoItemTextColor
)

val WatchNowButtonTextStyle = AppTypography.labelLarge.copy(
    color = TodoItemTextColor
)

val MovieOverviewBodyTextStyle = AppTypography.bodyMedium.copy(
    color = TodoItemTextColor
)

val MovieHeaderTextStyle = AppTypography.titleMedium.copy(
    color = surfaceVariantDark
)

val TodayHeaderTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
    letterSpacing = 0.5.sp,
    color = surfaceVariantDark
)

val DateSubHeaderTextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.5.sp,
    color = surfaceVariantDark
)

val TodoItemTitleTextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    letterSpacing = 0.5.sp,
    color = TodoItemTextColor
)

val TodoInputBarTextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
    letterSpacing = 0.5.sp,
    color = Color.Black
)