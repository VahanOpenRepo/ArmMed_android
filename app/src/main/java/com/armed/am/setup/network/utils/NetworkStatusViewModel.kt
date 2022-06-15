package com.armed.am.setup.network.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers

/**
 * Created by Levon Arzumanyan on 12/2/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class NetworkStatusViewModel(
    networkStatusTracker: NetworkStatusTracker,
) : ViewModel() {

    val state = networkStatusTracker.networkStatus
        .map(
            onUnavailable = { MyState.Error },
            onAvailable = { MyState.Fetched },
        )
        .asLiveData(Dispatchers.IO)
}