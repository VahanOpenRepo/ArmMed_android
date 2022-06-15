package com.armed.am.patients.network.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.armed.am.patients.network.model.PatientUIModel
import com.armed.am.patients.network.model.PatientsListResponseModel
import com.armed.am.patients.network.service.PatientsListApiService
import com.armed.am.utils.Preferences
import com.armed.am.utils.Preferences.Companion.IS_MI_LIST_ADDED
import com.armed.am.utils.toPatientUIModel
import org.json.JSONObject

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientsListPagingSource(
    private val preferences: Preferences,
    private val patientsListApiService: PatientsListApiService,
    private val query: JSONObject
) : PagingSource<Int, PatientUIModel>() {

    override fun getRefreshKey(state: PagingState<Int, PatientUIModel>): Int? {
        //last visible position
        val anchorPosition = state.anchorPosition ?: return null
        //closest page to last visible position
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        //return current page position
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PatientUIModel> {
        val page: Int = params.key ?: 0
        val pageSize: Int = params.loadSize

        return try {
            val response = patientsListApiService.getPatientsList(
                from = (page * pageSize).toString(),
                direction = "-1",
                count = pageSize.toString(),
                first = if (page == 0) "1" else "0",
                filter = query.toString()
            )

            //bad solution need to be fixed in future
            if (preferences.getBooleanFromPreference(IS_MI_LIST_ADDED).not()) {
                saveMiListInPreferences(response.body()?.result?.mi)
                preferences.putBooleanInPreference(IS_MI_LIST_ADDED, true)
            }

            val patients = response.body()!!.result.patientsList.map { it.toPatientUIModel() }
            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (patients.size < pageSize) null else page + 1
            LoadResult.Page(patients, prevKey, nextKey)
        } catch (ex: Exception) {
            LoadResult.Error(Exception(ex))
        }
    }

    //this list includes clinic names where doctor works
    // uuid and id
    private fun saveMiListInPreferences(miList: List<PatientsListResponseModel.Result.Mi>?) {
        preferences.putListInPreference(Preferences.MI_LIST, miList)
    }

}