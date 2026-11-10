package com.codenytra.amme.dermaxrai

import android.app.Application
import com.codenytra.amme.dermaxrai.features.settings.ThemeViewModel

class DermaApp : Application() {

    // App-wide ThemeViewModel instance, accessible by all Activities
    lateinit var themeViewModel: ThemeViewModel
        private set

    override fun onCreate() {
        super.onCreate()
        // Initialize the singleton ThemeViewModel with the Application context
        themeViewModel = ThemeViewModel(this)
        instance = this
    }

    companion object {
        // Global singleton accessor to the Application instance
        lateinit var instance: DermaApp
            private set
    }
}