package com.armed.am.patients.presentation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.armed.am.authorization.network.model.LoginResponseModel
import com.armed.am.base.BaseViewModel
import com.armed.am.patients.network.model.PatientsListResponseModel
import com.armed.am.patients.repository.PatientsRepository
import com.armed.am.utils.GsonUtils.getProfileModelFromJson
import com.armed.am.utils.Preferences
import com.armed.am.utils.Preferences.Companion.MI_LIST
import org.json.JSONObject

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientsViewModel(
    private val patientsRepository: PatientsRepository,
    private val preferences: Preferences
) : BaseViewModel() {

    fun getPatients(query: JSONObject?) = Pager(
        PagingConfig(pageSize = 5),
        pagingSourceFactory = { patientsRepository.getPagingSource(query) }
    ).flow

    fun isUserLoggedIn(): Boolean {
        val profileUIModel: LoginResponseModel.Result? =
            preferences.getStringFromPreference(
                Preferences.PROFILE_UI_MODEL
            ).getProfileModelFromJson()

        return profileUIModel?.longToken?.isNotEmpty() ?: false
    }

    fun getMiList() =
        preferences.getListFromPreference<PatientsListResponseModel.Result.Mi>(MI_LIST)

    fun validateFilterClinicsDropdownInput(selectionId: Int) = selectionId >= 0

}