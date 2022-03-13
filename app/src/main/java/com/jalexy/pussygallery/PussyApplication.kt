package com.jalexy.pussygallery

import android.app.Application
import android.content.Context

class PussyApplication : Application() {

    companion object {
        const val API_KEY = "8c26fac0-0ab5-4b1e-8a7e-74998f76e1d9"

        const val PREFERENCE_NAME = "APPLICATION_PREFERENCE"
        const val USER_ID_KEY = "USER_ID"

        var USER_ID: String? = null
    }

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        USER_ID = sharedPreferences.getString(USER_ID_KEY, "")

        USER_ID.isNullOrEmpty().let {
            USER_ID = generateUserId()
            val editor = sharedPreferences.edit()
            editor.putString(USER_ID_KEY, USER_ID)
            editor.apply()
        }
    }

    private fun generateUserId(): String {
        val length = (8..12).random()
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}