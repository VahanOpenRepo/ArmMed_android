package com.armed.am.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Preferences(private val preferences: SharedPreferences, private val gson: Gson) {

    fun putStringInPreference(key: String, value: String) =
        preferences.edit().putString(key, value).apply()

    fun putBooleanInPreference(key: String, value: Boolean) =
        preferences.edit().putBoolean(key, value).apply()

    fun getStringFromPreference(key: String) = preferences.getString(key, "").orEmpty()

    fun getBooleanFromPreference(key: String) = preferences.getBoolean(key, false)

    fun <T> putListInPreference(key: String, list: List<T>?) {
        val listJson = gson.toJson(list)
        putStringInPreference(key, listJson)
    }

    inline fun <reified T> getListFromPreference(key: String): ArrayList<T>? =
        try {
            val listJson = getStringFromPreference(key)
            val type = object : TypeToken<ArrayList<T>>() {}.type
            Gson().fromJson(listJson, type)
        } catch (e: Exception) {
            null
        }

    companion object {
        const val SSO_TOKEN_KEY = "sso_token"
        const val PROFILE_UI_MODEL = "profile_ui_model"
        const val USER_ID = "user_id"
        const val TOKEN = "token"
        const val NAME_SURE_NAME = "name"
        const val EMAIL = "email"
        const val MI_LIST = "mi_list"
        const val IS_MI_LIST_ADDED = "isMiListAdded"
        const val CLINIC_NAMES_LIST = "clinic_names_list"
    }

    fun clearValues() {
        if (fileExist) {
            preferences.edit().clear().apply()
        }
    }

    val fileExist: Boolean
        get() = preferences.all.isNotEmpty()
}

