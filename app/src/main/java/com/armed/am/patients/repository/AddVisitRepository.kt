package com.armed.am.patients.repository

import com.armed.am.patients.network.model.AddVisitRequestModel
import com.armed.am.patients.network.model.AddVisitResponseModel
import com.armed.am.patients.network.model.GetPersonSuggestionResponseModel
import com.armed.am.patients.network.service.AddVisitApiService
import com.armed.am.patients.network.service.GetPersonSuggestionApiService
import com.armed.am.setup.network.helpers.QResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Levon Arzumanyan on 1/30/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

class AddVisitRepository(
    private val addVisitApiService: AddVisitApiService,
    private val getPersonSuggestionApiService: GetPersonSuggestionApiService
) {
    fun addVisit(addVisitRequestModel: AddVisitRequestModel): Flow<QResult<AddVisitResponseModel>> =
        flow {
            emit(QResult.Loading)
            val result = try {
                val response =
                    addVisitApiService.addVisit(
                        addVisitRequestModel.role_uuid,
                        addVisitRequestModel.patient_social_card,
                        addVisitRequestModel.patient_mobile_number,
                        addVisitRequestModel.visit_start,
                        addVisitRequestModel.visit_end,
                    addVisitRequestModel.visit_mode.toString(),
                    addVisitRequestModel.visit_type.toString(),
                    addVisitRequestModel.reason_of_the_visit.toString(),
                    addVisitRequestModel.source_of_financing.toString(),
                    addVisitRequestModel.health_issue_type,
                    addVisitRequestModel.health_issue_case_type.toString(),
                    addVisitRequestModel.health_issue_start_date,
                    addVisitRequestModel.health_issue_case_number,
                        addVisitRequestModel.patient_email,
                        addVisitRequestModel.patient_last_name_EN,
                        addVisitRequestModel.patient_first_name_EN
                    )
                QResult.Success(response)
            } catch (e: Exception) {
                QResult.Fail(e)
            }
            emit(result)
        }

    fun getPersonSuggestion(searchText: String): Flow<QResult<GetPersonSuggestionResponseModel>> =
        flow {
            emit(QResult.Loading)
            val result = try {
                val response = getPersonSuggestionApiService.getPersonSuggestion(searchText)
                QResult.Success(response)
            } catch (e: Exception) {
                QResult.Fail(e)
            }
            emit(result)
        }
}