package com.codenytra.amme.dermaxrai.core.utils

import android.content.Context
import androidx.core.content.edit

private const val PREFS_ONBOARDING = "onboarding"
private const val KEY_DONE = "onboarding_done"

fun isOnboardingDone(context: Context): Boolean {
    return context
        .getSharedPreferences(PREFS_ONBOARDING, Context.MODE_PRIVATE)
        .getBoolean(KEY_DONE, false)
}

fun markOnboardingDone(context: Context) {
    context
        .getSharedPreferences(PREFS_ONBOARDING, Context.MODE_PRIVATE)
        .edit {
            putBoolean(KEY_DONE, true)
        }
}