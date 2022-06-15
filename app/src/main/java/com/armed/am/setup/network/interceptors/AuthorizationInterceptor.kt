package com.armed.am.setup.network.interceptors

import com.armed.am.utils.GsonUtils.getProfileModelFromJson
import com.armed.am.utils.Preferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class AuthorizationInterceptor(private val preferences: Preferences) : Interceptor {
    private val authorizationHeaderKey = "Authorization"
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        return chain.proceed(
            request.newBuilder().apply {
                val profileModelJson:String=preferences.getStringFromPreference(Preferences.PROFILE_UI_MODEL)
                val token = if (profileModelJson.isNotEmpty()) profileModelJson.getProfileModelFromJson()?.longToken else ""
                if (token.isNullOrEmpty().not()) {
                    addHeader(authorizationHeaderKey, "Bearer $token")
                }
            }.build()
        )
    }
}