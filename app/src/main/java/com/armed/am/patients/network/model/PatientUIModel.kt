package com.armed.am.patients.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

@Parcelize
data class PatientUIModel(
    // 1 for male 2 for female
    val genderCode:String,
    val name:String,
    val sureName:String,
    val dateOfBirth:String,
    val mobilePhoneNumber:String,
    val passport:String,
    val address:String,
    val imageUrl:String,
    val visitStart:String,
    val visitEnd:String,
    val email:String,
    val caseType:String,
    val socialCard:String,
    val socialGroup:String,
    val diagnose:String,
):Parcelable