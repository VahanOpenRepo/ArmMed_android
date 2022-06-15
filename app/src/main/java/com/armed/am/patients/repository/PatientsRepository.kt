package com.armed.am.patients.repository

import androidx.paging.PagingSource
import com.armed.am.patients.network.model.PatientUIModel
import com.armed.am.patients.network.pagingSource.PatientsListPagingSource
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientsRepository: KoinComponent {
    fun getPagingSource(query: JSONObject?): PagingSource<Int, PatientUIModel> {
        val pagingSourceFactory: PatientsListPagingSource by inject { parametersOf(query) }
        return pagingSourceFactory
    }
}