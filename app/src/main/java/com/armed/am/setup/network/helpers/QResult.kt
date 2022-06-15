package com.armed.am.setup.network.helpers

sealed class QResult<out T> {

    /**
     * Created by Levon Arzumanyan on 09/18/21.
     * Project Name: ARMED
     * NOORLOGIC
     */

    object Loading : QResult<Nothing>()

    data class Success<out T>(val value: T) : QResult<T>()

    data class Fail(val error:Exception): QResult<Nothing>()

}