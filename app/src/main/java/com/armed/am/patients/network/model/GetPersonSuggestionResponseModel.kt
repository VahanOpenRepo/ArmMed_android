package com.armed.am.patients.network.model

/**
 * Created by Levon Arzumanyan on 2/12/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class GetPersonSuggestionResponseModel(
    val date_time: Any?,
    val date_time_msecs: Any?,
    val db_duration: Any?,
    val duration: Any?,
    val error: Any?,
    val log: Log?,
    val node_id: Any?,
    val node_id_ex: Any?,
    val params: List<Any?>?,
    val queue_size: Any?,
    val rdb_duration: Any?,
    val result: List<Result?>?,
    val status: Int?,
    val unique_id: Any?,
    val user: String?,
    val waiting_time: Any?
) {
    data class Log(
        val logs: List<Any?>?
    )

    data class Result(
        val DocumentDepartment: String?,
        val DocumentType: String?,
        val IssuanceDate: String?,
        val ValidityDate: String?,
        val addresses: List<Addresse?>?,
        val all_birth_dates: List<String?>?,
        val all_emails: List<Any?>?,
        val all_mobile_numbers: List<Any?>?,
        val all_passports: List<String?>?,
        val date_of_birth: String?,
        val death_date: String?,
        val first_name: String?,
        val fullname_english: String?,
        val gender: String?,
        val gender_AM: String?,
        val gender_EN: String?,
        val gender_RU: String?,
        val gender_code: String?,
        val gender_label: String?,
        val is_base64: String?,
        val last_name: String?,
        val other_basic_information: List<OtherBasicInformation?>?,
        val passport: String?,
        val patronymic_name: String?,
        val profile_picture: String?,
        val social_card: String?,
        val sources: List<Any?>?,
        val surname_english: String?,
        val uuid: Any?
    ) {
        data class Addresse(
            val Apartment: String?,
            val Building: String?,
            val BuildingType: String?,
            val Community: String?,
            val Region: String?,
            val Street: String?,
            val home_0032address: String?,
            val is_city: String?,
            val region_code: String?,
            val type_code: String?
        )

        data class OtherBasicInformation(
            val citizenship: List<Citizenship?>?
        ) {
            data class Citizenship(
                val citizenship_code: String?
            )
        }
    }
}