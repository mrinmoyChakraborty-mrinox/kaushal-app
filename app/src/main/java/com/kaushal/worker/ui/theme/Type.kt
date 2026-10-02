package com.kaushal.worker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val KaushalTypography = Typography().run {
    copy(
        headlineLarge = headlineLarge.copy(fontSize = 30.sp, fontWeight = FontWeight.Bold, color = KaushalNavy),
        headlineMedium = headlineMedium.copy(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = KaushalNavy),
        titleLarge = titleLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = KaushalNavy),
        titleMedium = titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
        bodyLarge = bodyLarge.copy(fontSize = 16.sp),
        bodyMedium = bodyMedium.copy(fontSize = 14.sp),
        labelLarge = labelLarge.copy(fontWeight = FontWeight.Bold)
    )
}
