package com.armed.am.patients.network.model

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class PatientsListRequestModel(
    val from: String,
    //if -1 then requests previous visits if 1 then future visits
    val direction: String,
    val count: String,
    //will send 1 only on first request the should send empty
    val first: String
)
