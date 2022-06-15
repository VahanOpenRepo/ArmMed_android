package com.armed.am.patients.network.model

/**
 * Created by Levon Arzumanyan on 1/23/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class AddVisitRequestModel(
    val role_uuid: String="",
    val patient_social_card: String,
    val patient_mobile_number: String,
    val visit_start: String,
    val visit_end: String,
    val visit_mode: Int,
    val visit_type: Int,
    val reason_of_the_visit: Int,
    val source_of_financing: Int,
    val health_issue_type: String,
    val health_issue_case_type: Int,
    val health_issue_start_date: String,
    val health_issue_case_number: String,
    val patient_email: String,
    val patient_last_name_EN: String,
    val patient_first_name_EN: String,
)
