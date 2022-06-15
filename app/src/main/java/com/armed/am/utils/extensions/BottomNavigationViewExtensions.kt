package com.armed.am.utils

import android.widget.ImageView
import com.armed.am.R
import com.armed.am.main.presentation.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

/**
 * Created by Andranik Yeghoyan on 12/25/20.
 * Project Name: UAE TRA
 */

/**
 * Making Top-Left and Top-Right rounded corners based on radius
 */
fun BottomNavigationView.setupBackground(radius: Float) {
    val bottomNavigationViewBackground = background as MaterialShapeDrawable
    bottomNavigationViewBackground.shapeAppearanceModel =
        bottomNavigationViewBackground.shapeAppearanceModel.toBuilder()
            .build()
}

/**
 * Customize Menu items of bottom navigation view
 * Adding custom view as background selector
 * Changing assistant view
 */
fun BottomNavigationView.setupMenuItems() {
    val bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView

    when (bottomNavigationMenuView.childCount) {
        0 -> {
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.ic_patient)

            // add to container
            addView(imageView)
        }

        1 -> {
            val imageView = ImageView(context)
            imageView.setImageResource(R.drawable.ic_profile)

            // add to container
            addView(imageView)
        }
    }

}

fun BottomNavigationView.selectTab(tab: MainActivity.BottomNavigationTab) {
    val bottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
    val index = tab.index
    if (bottomNavigationMenuView.childCount > index) {
        val item = (bottomNavigationMenuView.getChildAt(index) as BottomNavigationItemView)
        item.performClick()
    }
}

