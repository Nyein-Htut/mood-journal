package com.example.moodjournal.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Stores the user's Groq API key on-device using AndroidX Security's
 * EncryptedSharedPreferences. The key never leaves the device except
 * as part of the user's own direct HTTPS calls to Groq.
 */
class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var groqApiKey: String?
        get() = prefs.getString(KEY_GROQ_API, "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")
        set(value) = prefs.edit().putString(KEY_GROQ_API, value).apply()

    var isDarkMode: Boolean
        get() = prefs.getBoolean(KEY_DARK_MODE, false)
        set(value) = prefs.edit().putBoolean(KEY_DARK_MODE, value).apply()

    var isGuestMode: Boolean
        get() = prefs.getBoolean(KEY_GUEST_MODE, false)
        set(value) = prefs.edit().putBoolean(KEY_GUEST_MODE, value).apply()

    companion object {
        private const val KEY_GROQ_API = "groq_api_key"
        private const val KEY_DARK_MODE = "is_dark_mode"
        private const val KEY_GUEST_MODE = "is_guest_mode"
    }
}
