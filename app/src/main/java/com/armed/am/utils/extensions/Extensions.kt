package com.armed.am.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.armed.am.R
import com.armed.am.main.presentation.MainActivity
import com.armed.am.setup.permissions.AppPermissions
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Levon Arzumanyan on 9/20/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

fun Context.isPermissionGranted(@AppPermissions permission: String): Boolean =
    (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)

fun Fragment.isPermissionGranted(@AppPermissions permission: String) =
    requireActivity().isPermissionGranted(permission)

fun Context.arePermissionsGranted(@AppPermissions vararg permission: String): Boolean {
    permission.forEach {
        if (isPermissionGranted(it).not()) {
            return false
        }
    }
    return true
}

fun FragmentActivity.showInfoDialog(
    message: String?,
    title: String?,
    positiveButtonText: String = (this as MainActivity).getString(R.string.button_ok),
    negativeButtonText: String = (this as MainActivity).getString(R.string.button_cancel),
    positiveButtonClickListener: (() -> Unit)? = null,
    negativeButtonClickListener: (() -> Unit)? = null,
) {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)

        if (positiveButtonClickListener != null) {
            setPositiveButton(positiveButtonText)
            { _, _ ->
                positiveButtonClickListener()
            }
        }

        if (negativeButtonClickListener != null) {
            setNegativeButton(negativeButtonText)
            { _, _ -> negativeButtonClickListener() }
        }
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

fun Fragment.showInfoDialog(
    message: String?,
    title: String?,
    positiveButtonText: String = getString(R.string.button_ok),
    negativeButtonText: String = getString(R.string.button_cancel),
    positiveButtonClickListener: (() -> Unit)? = null,
    negativeButtonClickListener: (() -> Unit)? = null,
) = requireActivity().showInfoDialog(
    message,
    title,
    positiveButtonText,
    negativeButtonText,
    positiveButtonClickListener,
    negativeButtonClickListener
)

fun Fragment.showInfoDialog(
    @StringRes messageRes: Int,
    @StringRes titleRes: Int,
    @StringRes positiveButtonTextRes: Int = R.string.button_ok,
    @StringRes negativeButtonTextRes: Int = R.string.button_cancel,
    positiveButtonClickListener: (() -> Unit)? = null,
    negativeButtonClickListener: (() -> Unit)? = null,
) {
    showInfoDialog(
        getString(messageRes),
        getString(titleRes),
        getString(positiveButtonTextRes),
        getString(negativeButtonTextRes),
        positiveButtonClickListener,
        negativeButtonClickListener
    )
}

fun showLongToast(text: String, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun Fragment.logout() {
    (activity as MainActivity).logout()
}

fun String.formatDate(fromFormat: String, toFormat: String): String {
    return try {
        val originalFormat = SimpleDateFormat(fromFormat)
        val targetFormat = SimpleDateFormat(toFormat)
        val date = originalFormat.parse(this)
        targetFormat.format(date)
    } catch (e: Exception) {
        Log.e("ParseError", e.message.toString())
        ""
    }
}

fun String.parseToDate(datePattern: String): Date = try {
    SimpleDateFormat(datePattern, Locale.getDefault()).parse(this) ?: Date()
} catch (e: Exception) {
    Date()
}

fun String.parseToLocalDateTime(dateTimePattern:String):LocalDateTime=try {
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(dateTimePattern))
} catch (e:Exception){
    Log.e("ParseError",e.message.toString(),e)
    LocalDateTime.now()
}

fun String.getYearsFromDate(datePattern: String): String {
    val myDate =
        this.parseToDate(datePattern).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val currentDate = LocalDate.now()
    return Period.between(myDate, currentDate).years.toString()
}

fun <T> MutableLiveData<T>.toImmutableLiveData(): LiveData<T> {
    val liveData: LiveData<T> = this
    return liveData
}

fun dateToSqlFormat(year: Int, month: Int, day: Int) = "$year-$month-$day"
fun timeToSqlFormat(hourOfDay: Int, minute: Int): String {
    val hourOfDayFormatted: String = if (hourOfDay < 10) "0$hourOfDay" else hourOfDay.toString()
    val minuteFormatted: String = if (minute < 10) "0$minute" else minute.toString()
    return "$hourOfDayFormatted:$minuteFormatted:00"
}

fun timeToSqlFormatWithoutSeconds(hourOfDay: Int, minute: Int): String {
    val hourOfDayFormatted: String = if (hourOfDay < 10) "0$hourOfDay" else hourOfDay.toString()
    val minuteFormatted: String = if (minute < 10) "0$minute" else minute.toString()
    return "$hourOfDayFormatted:$minuteFormatted"
}

fun String.showPopupIfEmpty(context: Context): Boolean =
    if (this.isNullOrEmpty()) {
        showLongToast("Please fill all fields", context)
        true
    } else false

fun Fragment.showLoading() {
    activity?.let { (it as MainActivity).showLoading() }
}

fun Fragment.hideLoading() {
    activity?.let { (it as MainActivity).hideLoading() }
}

fun String?.safeToDouble() = try {
    this?.toDouble()?:0.0
} catch (e:NumberFormatException){
    0.0
}

fun isInputValueValid(
    minValue: Double = 0.0,
    maxValue: Double = 0.0,
    inputValue: Double = 0.0,
    context: Context
) = if (inputValue in minValue..maxValue) true else {
    showLongToast(context.getString(R.string.please_fill_valid_values), context)
    false
}



