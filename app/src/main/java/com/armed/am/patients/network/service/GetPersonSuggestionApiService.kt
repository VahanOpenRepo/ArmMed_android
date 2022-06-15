package com.armed.am.patients.network.service

import com.armed.am.patients.network.model.GetPersonSuggestionResponseModel
import com.armed.am.utils.Constants.FORMAT_JSON
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Levon Arzumanyan on 2/12/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

interface GetPersonSuggestionApiService {
    @Multipart
    @POST("/am/hwmobile/getpersonsuggestions")
    suspend fun getPersonSuggestion(
        @Part("searchtext") searchText: String,
        @Part("format") format: String = FORMAT_JSON,
    ): GetPersonSuggestionResponseModel
}