package com.armed.am.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.doOnDetach
import androidx.core.view.isVisible
import com.armed.am.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.roundToInt

/**
 * Created by Levon Arzumanyan on 10/17/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

fun pxToDp(px: Int, resources: Resources) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        px.toFloat(),
        resources.displayMetrics
    ).toInt()


fun dpToPx(context: Context, dp: Float): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density).roundToInt()
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun ImageView.displayImage(url: String?, @DrawableRes defaultImage: Int = 0) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(context)
            .load(url)
            .error(defaultImage)
            .into(this)
    } else {
        setImageResource(defaultImage)
    }
}

fun ImageView.displayRoundedImage(
    url: String?,
    radius: Int = 300,
    defaultImage: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_profile_placeholder)
) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(context)
            .load(url)
            .placeholder(defaultImage)
            .error(defaultImage)
            .transform(CenterCrop(), RoundedCorners(radius))
            .into(this)
    } else if (url == "") {
        displayRoundedImage(defaultImage, radius)
    }
}

fun ImageView.displayRoundedImage(
    defaultImage: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_profile_placeholder),
    radius: Int = 300
) {
    Glide.with(context)
        .load(defaultImage)
        .placeholder(defaultImage)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(this)
}

/**
 * This extension is going to help avoid multiple quick clicks
 */

inline fun View.onClick(
    debounceDelay: Long = 750L,
    crossinline onClickDebounce: (View) -> Unit,
) {
    val debounceHandler = Handler(Looper.getMainLooper())
    val notClicked = AtomicBoolean(true)
    doOnDetach {
        notClicked.set(true)
    }
    setOnClickListener { view ->
        if (notClicked.get()) {
            notClicked.set(false)
            onClickDebounce(view)
            debounceHandler.postDelayed({
                notClicked.set(true)
            }, debounceDelay)
        }
    }
}

fun areInputsEmpty(context: Context, vararg textInputLayout: TextInputLayout): Boolean {
    var allInputsAreValid = true
    textInputLayout.forEach {
        if (it.isVisible && it.isInputValid().not()) {
            showLongToast(context.getString(R.string.please_fill_in_all_fields), context)
            allInputsAreValid = false
            return false
        }
    }
    return allInputsAreValid
}

fun TextInputLayout.isInputValid() =
    this.editText?.text.isNullOrEmpty().not()

 fun AutoCompleteTextView.getSelectedItemPosition() =
    (this.adapter as ArrayAdapter<String>).getPosition(this.text.toString())