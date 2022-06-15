package com.armed.am.patients.network.model

/**
 * Created by Levon Arzumanyan on 2/12/22.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class GetPersonSuggestionUIModel(
    val name: String? = "",
    val surname: String? = "",
    val socialCard: String? = ""
)