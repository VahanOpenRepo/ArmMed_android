package com.armed.am.patients.network.service
 import com.armed.am.patients.network.model.PatientsListResponseModel
 import retrofit2.Response
 import retrofit2.http.Multipart
 import retrofit2.http.POST
 import retrofit2.http.Part

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

interface PatientsListApiService {
    @Multipart
    @POST("/hwmobile/getvisits")
    suspend fun getPatientsList(
        @Part("from") from: String,
        @Part("direction") direction: String,
        @Part("count") count: String,
        @Part("first") first: String,
        @Part ("filter") filter:String):Response<PatientsListResponseModel>
}