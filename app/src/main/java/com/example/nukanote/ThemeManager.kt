package com.example.nukanote

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ThemeManager {
    private const val PREFS_NAME = "theme_preferences"
    private const val BACKGROUND_COLOR_KEY = "backgroundColor"
    private val _themeChanged = MutableLiveData<Unit>()
    val themeChanged: LiveData<Unit> get() = _themeChanged

    fun getBackgroundColor(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(BACKGROUND_COLOR_KEY, 0xFFFFFFFF.toInt())
    }

    fun saveTheme(context: Context, backgroundColor: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(BACKGROUND_COLOR_KEY, backgroundColor)
            apply()
        }
        _themeChanged.value = Unit
    }
}