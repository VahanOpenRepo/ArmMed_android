package com.armed.am.patients.network.service

import com.armed.am.patients.network.model.AddVisitResponseModel
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Levon Arzumanyan on 1/23/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

interface AddVisitApiService {
    @Multipart
    @POST("/am/hwmobile/add_visit")
    suspend fun addVisit(
        @Part("role_uuid") role_uuid: String,
        @Part("patient_social_card") patient_social_card: String,
        @Part("patient_mobile_number") patient_mobile_number: String,
        @Part("visit_start") visit_start: String,
        @Part("visit_end") visit_end: String,
        @Part("visit_mode") visit_mode: String,
        @Part("visit_type") visit_type: String,
        @Part("reason_of_the_visit") reason_of_the_visit: String,
        @Part("source_of_financing") source_of_financing: String,
        @Part("health_issue_type") health_issue_type: String,
        @Part("health_issue_case_type") health_issue_case_type: String,
        @Part("health_issue_start_date") health_issue_start_date: String,
        @Part("health_issue_case_number") health_issue_case_number: String,
        @Part("patient_email") patient_email: String,
        @Part("patient_last_name_EN") patient_last_name_EN: String,
        @Part("patient_first_name_EN") patient_first_name_EN: String
    ): AddVisitResponseModel
}