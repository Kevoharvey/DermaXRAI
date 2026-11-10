package com.codenytra.amme.dermaxrai.core.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface StepMeta {
    data class Label(
        val icon: ImageVector,
        @StringRes val text: Int,
        val positive: Boolean = false
    ) : StepMeta

    data class Tag(@StringRes val text: Int) : StepMeta
}

data class RoutineStep(
    val number: Int,
    @StringRes val category: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val icon: ImageVector,
    val meta: StepMeta,
    val footerIcon: ImageVector,
    @StringRes val footerText: Int,
    val footerPositive: Boolean = false,
    @StringRes val action: Int
)