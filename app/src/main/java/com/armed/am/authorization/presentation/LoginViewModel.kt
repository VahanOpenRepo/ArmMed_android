package com.armed.am.authorization.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.armed.am.authorization.network.model.LoginResponseModel
import com.armed.am.authorization.repository.LoginRepository
import com.armed.am.base.BaseViewModel
import com.armed.am.setup.network.helpers.QResult
import com.armed.am.utils.toImmutableLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Hrant Arzumanyan on 09/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val isLoggedSuccessfullyLiveData = MutableLiveData<LoginResponseModel.Result?>()

    fun login(loginQrResult: String) = viewModelScope.launch {
        loginRepository.login(loginQrResult).collect {
            when (it) {
                is QResult.Loading -> isLoadingLiveData.value = true
                is QResult.Fail -> {
                    errorMessageLiveData.value = it.error.message
                    isLoadingLiveData.value = false
                }
                is QResult.Success -> {
                    isLoggedSuccessfullyLiveData.value = it.value
                    isLoadingLiveData.value = false
                }
            }
        }
    }

    fun getLoginResultLiveData() = isLoggedSuccessfullyLiveData.toImmutableLiveData()
    fun getLoadingLiveData() = isLoadingLiveData.toImmutableLiveData()
    fun getErrorLiveData() = errorMessageLiveData.toImmutableLiveData()
}