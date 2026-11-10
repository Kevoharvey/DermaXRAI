package com.codenytra.amme.dermaxrai.core.utils

import android.content.Context
import androidx.core.content.edit

enum class ThemeMode { SYSTEM, LIGHT, DARK }

object ThemePreferences {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_THEME_MODE  = "theme_mode"

    fun getThemeMode(context: Context): ThemeMode {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)

        return try {
            enumValueOf(saved ?: ThemeMode.SYSTEM.name)
        } catch (_: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    fun setThemeMode(context: Context, themeMode: ThemeMode) {
        val prefs = context.getSharedPreferences(ThemePreferences.PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(ThemePreferences.KEY_THEME_MODE, themeMode.name) }
    }
}
