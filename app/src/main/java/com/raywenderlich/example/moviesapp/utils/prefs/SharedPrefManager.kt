package com.raywenderlich.example.moviesapp.utils.prefs

import android.content.Context
import com.raywenderlich.example.moviesapp.App

class SharedPrefManager {
    private val context = App.getAppContext()
    private val prefs = context.getSharedPreferences(MOVIES_SHARED_PREFS, Context.MODE_PRIVATE)

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(LOGIN_STATUS, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean = prefs.getBoolean(LOGIN_STATUS, false)
}

private const val MOVIES_SHARED_PREFS = "movies_prefs"
private const val LOGIN_STATUS = "login_status"