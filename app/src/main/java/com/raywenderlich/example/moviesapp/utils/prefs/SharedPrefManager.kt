package com.raywenderlich.example.moviesapp.utils.prefs

import android.content.Context
import com.raywenderlich.example.moviesapp.App
import com.raywenderlich.example.moviesapp.utils.LOGIN_STATUS
import com.raywenderlich.example.moviesapp.utils.MOVIES_SHARED_PREFS

class SharedPrefManager {
    private val context = App.getAppContext()
    private val prefs = context.getSharedPreferences(MOVIES_SHARED_PREFS, Context.MODE_PRIVATE)

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(LOGIN_STATUS, isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean = prefs.getBoolean(LOGIN_STATUS, false)
}