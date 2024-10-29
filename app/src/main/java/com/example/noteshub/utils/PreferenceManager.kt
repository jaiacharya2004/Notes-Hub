package com.example.noteshub.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREFS_NAME = "AppPrefs"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"

    // Lazy initialization of SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    // Initialize SharedPreferences (call this once in Application class or MainActivity)
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save login state
    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    // Retrieve login state
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}
