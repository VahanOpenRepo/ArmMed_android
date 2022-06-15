package com.armed.am.survey.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Levon Arzumanyan on 10/11/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

@Parcelize
data class QuestionParamsModel(
    @SerializedName("conditionVariable")
    val name: String = "",
    val max_value: String = "",
    val min_value: String = "",
    val question: String = "",
    val protocol_name: String = "",
    @SerializedName("file")
    val fileUrl: String = "",
    val dictionary_content: List<DictionaryContent> = emptyList(),
    val variable_type_code: String = ""
) : Parcelable {
    @Parcelize
    data class DictionaryContent(
        val code: String,
        val text: String,
        val text_AM: String,
        val text_EN: String,
        val text_RU: String
    ) : Parcelable
}
