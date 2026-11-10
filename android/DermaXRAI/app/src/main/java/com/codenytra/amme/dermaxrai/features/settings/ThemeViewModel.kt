package com.codenytra.amme.dermaxrai.features.settings

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.codenytra.amme.dermaxrai.core.utils.ThemeMode
import com.codenytra.amme.dermaxrai.core.utils.ThemePreferences

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    // ─── Theme State (Observable by Compose) ──────────────
    var themeMode by mutableStateOf(ThemePreferences.getThemeMode(application))
        private set

    fun updateThemeMode(mode: ThemeMode) {
        themeMode = mode
        ThemePreferences.setThemeMode(getApplication(), mode)
    }
}