package com.armed.am.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Created by Levon Arzumanyan on 9/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.openWebPage(url: String) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    startActivity(intent)
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun runDelayed(delay: Long, delayedFunction: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        delayedFunction.invoke()
    }, delay)
}