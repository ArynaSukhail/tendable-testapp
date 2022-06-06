package com.release.data.prefs

import android.content.Context
import javax.inject.Inject

class AppPrefs @Inject constructor(
    context: Context
) {

    private val prefsKey by lazy {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    }

    fun setEnteredKey(isUserEntered: Boolean) {
        prefsKey
            .edit()
            .putBoolean(USER_ENTERED_KEY, isUserEntered)
            .apply()
    }

    fun getEnteredKey(): Boolean {
        return prefsKey.getBoolean(USER_ENTERED_KEY, false)
    }

    companion object {
        private const val PREF_KEY = "PREF_KEY"
        private const val USER_ENTERED_KEY = "USER_ENTERED_KEY"
    }
}
