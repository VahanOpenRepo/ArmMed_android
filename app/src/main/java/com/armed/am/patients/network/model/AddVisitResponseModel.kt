package com.armed.am.patients.network.model

data class AddVisitResponseModel(
    var error: Error? = null,
    var result: Result? = null,
    var status: Int = 0
) {
    data class Result(
        var date_of_birth: String = "",
        var first_name: String = "",
        var gender_code: String = "",
        var health_issues: List<HealthIssue> = listOf(),
        var last_name: String = "",
        var passport: String = "",
        var patronymic_name: String = "",
        var social_card: String = "",
        var uuid: String = "",
        var visits: List<Visit> = listOf()
    ) {
        data class HealthIssue(
            var health_0032issue: List<Health0032issue> = listOf()
        ) {
            data class Health0032issue(
                var action_date: String = "",
                var case_number: String = "",
                var case_type_code: String = "",
                var name: String = "",
                var start_date: String = "",
                var type_code: String = "",
                var uuid: String = ""
            )
        }

        data class Visit(
            var visit: List<Visit> = listOf()
        ) {
            data class Visit(
                var doctor_ref_data: List<DoctorRefData> = listOf(),
                var is_cancelled: String = "",
                var reason_of_visit_code: String = "",
                var source_of_financing_code: String = "",
                var uuid: String = "",
                var visit_end: String = "",
                var visit_mode_code: String = "",
                var visit_start: String = "",
                var visit_type_code: String = ""
            ) {
                data class DoctorRefData(
                    var doctor_ref: List<DoctorRef> = listOf()
                ) {
                    data class DoctorRef(
                        var doctor_uuid: String = "",
                        var is_cancelled: String = "",
                        var uuid: String = "",
                        var visit_end: String = "",
                        var visit_start: String = ""
                    )
                }
            }
        }
    }

    data class Error(
        var customErrorMessage: String = "",
        var errorCode: String = "",
        var errorMessage: String = "",
        var errorType: Int = 0
    )
}