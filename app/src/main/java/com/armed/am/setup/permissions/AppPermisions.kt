package com.armed.am.setup.permissions

import android.Manifest
import androidx.annotation.StringDef
import com.armed.am.setup.permissions.AppPermissions.Companion.PERMISSION_CAMERA

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@StringDef(PERMISSION_CAMERA)
annotation class AppPermissions {

    companion object {
        const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    }

}