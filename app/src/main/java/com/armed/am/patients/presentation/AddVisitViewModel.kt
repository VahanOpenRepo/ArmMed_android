package com.armed.am.patients.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.armed.am.base.BaseViewModel
import com.armed.am.patients.network.model.AddVisitRequestModel
import com.armed.am.patients.network.model.GetPersonSuggestionUIModel
import com.armed.am.patients.repository.AddVisitRepository
import com.armed.am.setup.network.helpers.QResult
import com.armed.am.utils.Constants.FUTURE_DATE_IS_SELECTED
import com.armed.am.utils.Constants.NO_RESULTS_FOUND
import com.armed.am.utils.Constants.PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON
import com.armed.am.utils.Constants.REQUIRED_FIELD_IS_EMPTY
import com.armed.am.utils.Constants.START_DATE_IS_GREATER_THEN_END_DATE
import com.armed.am.utils.Constants.VALIDATION_SUCCESS
import com.armed.am.utils.parseToLocalDateTime
import com.armed.am.utils.toGetPersonSuggestionUIModel
import com.armed.am.utils.toImmutableLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * Created by Levon Arzumanyan on 1/30/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

class AddVisitViewModel(private val addVisitRepository: AddVisitRepository) : BaseViewModel() {

    private val isVisitAddedLiveData = MutableLiveData<Boolean>()
    private val errorCodeLiveData = MutableLiveData<Int>()
    private val getPersonSuggestionLiveData = MutableLiveData<GetPersonSuggestionUIModel>()

    fun addVisit(addVisitRequestModel: AddVisitRequestModel) {
        val validationValue = validateRequestModelData(addVisitRequestModel)
        if (validationValue == VALIDATION_SUCCESS) {
            viewModelScope.launch {
                addVisitRepository.addVisit(addVisitRequestModel).collect { response ->
                    when (response) {
                        is QResult.Loading -> isLoadingLiveData.value = true
                        is QResult.Fail -> {
                            errorMessageLiveData.value = response.error.message
                            isLoadingLiveData.value = false
                        }
                        is QResult.Success -> {
                            if (response.value.result==null){
                                isVisitAddedLiveData.value = false
                                errorMessageLiveData.value = response.value.error?.errorMessage
                            } else {
                                isVisitAddedLiveData.value = true
                            }
                            isLoadingLiveData.value = false
                        }
                    }
                }
            }
        } else {
            errorCodeLiveData.value = validationValue
        }
    }

    fun getPersonSuggestion(searchText: String) {
        viewModelScope.launch {
            addVisitRepository.getPersonSuggestion(searchText).collect { response ->
                when (response) {
                    is QResult.Loading -> isLoadingLiveData.value = true
                    is QResult.Fail -> {
                        errorMessageLiveData.value = response.error.message
                        isLoadingLiveData.value = false
                    }
                    is QResult.Success -> {
                        if (response.value.result.isNullOrEmpty().not()) {
                            getPersonSuggestionLiveData.value =
                                response.value.result!![0]?.toGetPersonSuggestionUIModel()
                        } else {
                            errorCodeLiveData.value = NO_RESULTS_FOUND
                        }
                        isLoadingLiveData.value = false
                    }
                }
            }
        }
    }

    private fun validateRequestModelData(requestModel: AddVisitRequestModel): Int {
        if (validateTextsOnNullOrEmpty(
                requestModel.role_uuid,
                requestModel.patient_social_card,
                requestModel.patient_mobile_number,
                requestModel.visit_start,
                requestModel.visit_end,
                requestModel.health_issue_type,
                requestModel.health_issue_start_date,
                requestModel.health_issue_case_number
            ).not()
        ) {
            return REQUIRED_FIELD_IS_EMPTY
        }

        if (validateIntegersOnMinus(
                requestModel.visit_mode,
                requestModel.visit_type,
                requestModel.reason_of_the_visit,
                requestModel.source_of_financing,
                requestModel.health_issue_case_type
            ).not()
        ) {
            return REQUIRED_FIELD_IS_EMPTY
        }

        if (validateVisitStartAndVisitEnd(
                requestModel.visit_start.parseToLocalDateTime(PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON),
                requestModel.visit_end.parseToLocalDateTime(PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON),
            ).not()
        ) {
            return START_DATE_IS_GREATER_THEN_END_DATE
        }

        if (validateIfDateIsNotFutureDate(
                "${requestModel.health_issue_start_date}:00".parseToLocalDateTime(
                    PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON
                )
            ).not()
        ) {
            return FUTURE_DATE_IS_SELECTED
        }

        return VALIDATION_SUCCESS
    }

    private fun validateTextsOnNullOrEmpty(vararg strings: String?): Boolean {
        strings.forEach {
            if (it?.trim().isNullOrEmpty()) {
                return false
            }
        }
        return true
    }

    private fun validateIntegersOnMinus(vararg ints: Int): Boolean {
        ints.forEach {
            if (it < 0) {
                return false
            }
        }
        return true
    }

    private fun validateVisitStartAndVisitEnd(
        visitStartDateTime: LocalDateTime,
        visitEndDateTime: LocalDateTime
    ): Boolean {
        return visitStartDateTime.compareTo(visitEndDateTime) < 1
    }

    private fun validateIfDateIsNotFutureDate(healthIssueStartDateTime: LocalDateTime): Boolean {
        return healthIssueStartDateTime.compareTo(LocalDateTime.now()) < 1
    }

    fun getIsVisitAddedLiveData() = isVisitAddedLiveData.toImmutableLiveData()
    fun getLoadingLiveData() = isLoadingLiveData.toImmutableLiveData()
    fun getErrorLiveData() = errorMessageLiveData.toImmutableLiveData()
    fun getErrorCodeLiveData() = errorCodeLiveData.toImmutableLiveData()
    fun getPersonSuggestionLiveData() = getPersonSuggestionLiveData.toImmutableLiveData()

}