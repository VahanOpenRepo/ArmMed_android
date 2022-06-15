package com.armed.am.patients.network.model
 import com.google.gson.annotations.SerializedName

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class PatientsListResponseModel(
    val date_time: Any,
    val date_time_msecs: Any,
    val db_duration: Any,
    val duration: Any,
    val error: Any,
    val log: Log,
    val node_id: Any,
    val node_id_ex: Any,
    val params: List<Any>,
    val queue_size: Any,
    val rdb_duration: Any,
    val result: Result,
    val status: Int,
    val unique_id: Any,
    val user: String,
    val waiting_time: Any
) {
    data class Log(
        val logs: List<Any>
    )

    data class Result(
        @SerializedName("array")
        val patientsList: List<Patient>,
        val dic: Dic,
        val future_arr: List<Any>,
        val mi: List<Mi>
    ) {
        data class Patient(
            @SerializedName("@id")
            val id: String,
            @SerializedName("@source_id")
            val source_id: String,
            val action_date: String,
            val date_of_birth: String,
            val department_name: String,
            val department_type: String,
            val department_type_code: String,
            val diagnose: String,
            val diagnose_code: String,
            val doctor_id: String,
            val doctor_last_name: String,
            val doctor_name: String,
            val hw_rp_id: String,
            val insurance_status: String,
            val mi_name: String,
            val order_date: String,
            val outcome: String,
            val outcome_code: String,
            val patient_address: String,
            val patient_email: String,
            val patient_finance_source: String,
            val patient_finance_source_code: String,
            val patient_gender: String,
            // 1 for male 2 for female
            val patient_gender_code: String,
            val patient_home_phone: String,
            val patient_id: String,
            val patient_last_name: String,
            val patient_mobile_number: String,
            val patient_mobile_phone: String,
            val patient_name: String,
            val patient_passport: String,
            val patient_patronymic_name: String,
            val patient_ssn: String,
            val picture: String,
            val profile_picture: String,
            val real_visit_end: String,
            val real_visit_start: String,
            val removed: String,
            val services: String,
            val status_from: String,
            val status_to: String,
            val subdivision_specialization: String,
            val subdivision_specialization_code: String,
            val visit_color_code: String,
            val visit_end: String,
            val visit_id: String,
            val visit_mode: String,
            val visit_mode_code: String,
            val visit_room: String,
            val visit_start: String,
            val visit_status: String,
            val visit_type: String,
            val visit_type_code: String,
            @SerializedName("~ident_ids")
            val ident_ids: IdentIds
        ) {
            data class IdentIds(
                val patient_id: String
            )
        }

        data class Dic(
            val case_type: List<CaseType>
        ) {
            data class CaseType(
                val id: String,
                val name: String
            )
        }

        data class Mi(
            val id: String,
            val name: String,
            val role_uuid: String
        )
    }
}