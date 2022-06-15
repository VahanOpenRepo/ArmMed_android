package com.armed.am.setup.network.utils

/**
 * Created by Levon Arzumanyan on 12/2/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

sealed class MyState {
    object Fetched : MyState()
    object Error : MyState()
}