package com.example.uts.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager147(context147: Context) {
    private val pref147: SharedPreferences = context147.getSharedPreferences(PREF_NAME_147, Context.MODE_PRIVATE)
    private val editor147: SharedPreferences.Editor = pref147.edit()

    companion object {
        private const val PREF_NAME_147 = "user_session_147"
        private const val KEY_IS_LOGGED_IN_147 = "is_logged_in_147"
        private const val KEY_USER_ID_147 = "user_id_147"
        private const val KEY_USERNAME_147 = "username_147"
        private const val KEY_NAMA_LENGKAP_147 = "nama_lengkap_147"
    }

    fun saveLoginSession147(userId147: Long, username147: String, namaLengkap147: String) {
        editor147.putBoolean(KEY_IS_LOGGED_IN_147, true)
        editor147.putLong(KEY_USER_ID_147, userId147)
        editor147.putString(KEY_USERNAME_147, username147)
        editor147.putString(KEY_NAMA_LENGKAP_147, namaLengkap147)
        editor147.apply()
    }

    fun isLoggedIn147(): Boolean {
        return pref147.getBoolean(KEY_IS_LOGGED_IN_147, false)
    }

    fun getUserId147(): Long {
        return pref147.getLong(KEY_USER_ID_147, -1L)
    }

    fun getUsername147(): String? {
        return pref147.getString(KEY_USERNAME_147, null)
    }

    fun getNamaLengkap147(): String? {
        return pref147.getString(KEY_NAMA_LENGKAP_147, null)
    }

    fun logout147() {
        editor147.clear()
        editor147.apply()
    }
}

