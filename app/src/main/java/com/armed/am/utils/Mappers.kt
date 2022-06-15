package com.armed.am.utils

import com.armed.am.patients.network.model.GetPersonSuggestionResponseModel
import com.armed.am.patients.network.model.GetPersonSuggestionUIModel
import com.armed.am.patients.network.model.PatientUIModel
import com.armed.am.patients.network.model.PatientsListResponseModel

internal fun PatientsListResponseModel.Result.Patient.toPatientUIModel(): PatientUIModel {
    return PatientUIModel(
        genderCode=patient_gender_code,
        name = patient_name,
        sureName = patient_last_name,
        dateOfBirth = date_of_birth,
        mobilePhoneNumber = patient_mobile_number,
        passport = patient_passport,
        address = patient_address,
        imageUrl = picture,
        visitStart = visit_start,
        visitEnd = visit_end,
        email = patient_email,
        caseType = visit_mode,
        socialGroup = patient_finance_source,
        socialCard = patient_ssn,
        diagnose = diagnose
    )
}

internal fun GetPersonSuggestionResponseModel.Result.toGetPersonSuggestionUIModel() = GetPersonSuggestionUIModel(
    name = first_name,
    surname = last_name,
    socialCard = social_card
)