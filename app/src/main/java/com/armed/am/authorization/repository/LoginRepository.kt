package com.armed.am.authorization.repository

import com.armed.am.authorization.network.model.LoginResponseModel
import com.armed.am.authorization.network.service.LoginApiService
import com.armed.am.setup.network.helpers.QResult
import com.armed.am.utils.GsonUtils
import com.armed.am.utils.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepository(
    private val loginApiService: LoginApiService,
    private val preferences: Preferences
) {

    fun login(loginQrResult: String): Flow<QResult<LoginResponseModel.Result>> = flow {
        emit(QResult.Loading)

        val result = try {
            val response = loginApiService.login(loginQrResult)
            preferences.putStringInPreference(
                Preferences.PROFILE_UI_MODEL,
                GsonUtils.profileModelToJson(
                    LoginResponseModel.Result(
                        response.result.dateOfBirth.orEmpty(),
                        response.result.email.orEmpty(),
                        response.result.firstName.orEmpty(),
                        response.result.lastName.orEmpty(),
                        response.result.login.orEmpty(),
                        response.result.longToken.orEmpty(),
                        response.result.mobileNumber.orEmpty(),
                        response.result.profilePicture.orEmpty(),
                        response.result.socialCard.orEmpty()
                    )
                )
            )

            QResult.Success(response.result)
        } catch (e: Exception) {
            QResult.Fail(e)
        }

        emit(result)
    }
}