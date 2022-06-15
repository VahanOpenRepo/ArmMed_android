package com.armed.am.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    protected  val errorMessageLiveData= MutableLiveData<String>()
    protected  val isLoadingLiveData= MutableLiveData<Boolean>()
}