package com.armed.am.survey.network.service

import com.armed.am.survey.network.model.QuestionsResponseModel
import org.json.JSONArray
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Levon Arzumanyan on 10/9/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

interface QuestionsApiService {

    @FormUrlEncoded
    @POST("/hwmobile/getquestions")
    suspend fun getQuestions(@Field("questions") questionsArray: JSONArray):QuestionsResponseModel
}