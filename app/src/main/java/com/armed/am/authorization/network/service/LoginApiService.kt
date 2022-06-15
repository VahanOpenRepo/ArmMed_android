package com.armed.am.authorization.network.service

import com.armed.am.authorization.network.model.LoginResponseModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @FormUrlEncoded
    @POST("/hwmobile/armed_login")
    suspend fun login(@Field("login") login: String): LoginResponseModel
}