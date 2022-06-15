package com.armed.am.authorization.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    val date_time: Any,
    val date_time_msecs: Any,
    val db_duration: Any,
    val duration: Any,
    val error: Any,
    val log: Log,
    val node_id: Any,
    val node_id_ex: Any,
    val params: List<Any>,
    val queue_size: Any,
    val rdb_duration: Any,
    @SerializedName("result")
    val result: Result,
    val status: Int,
    val unique_id: Any,
    val user: String,
    val waiting_time: Any
) {

    data class Log(
        val logs: List<Any>
    )

    @Keep
    data class Result(
        @SerializedName("date_of_birth")
        val dateOfBirth: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("login")
        val login: String?,
        @SerializedName("long_token")
        val longToken: String?,
        @SerializedName("mobile_number")
        val mobileNumber: String?,
        @SerializedName("profile_picture")
        val profilePicture: String?,
        @SerializedName("social_card")
        val socialCard: String?
    )

    //"first_name": "ԱՆՆԱ",
    //        "last_name": "ՊԵՏՐՈՍՅԱՆ",
    //        "date_of_birth": "1992-06-05",
    //        "login": "5506920024",
    //        "profile_picture": "https://preprod.armed.am/sfm.php?s=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7Im9iamVjdF9pZCI6IjQ1MDU3OTg2Njc2ODQ1NzMiLCJmaWxlX25hbWUiOiI0NTA1Nzk4NjUwNjc0MjY1In0sImlzcyI6ImFybWVkIiwic3ViIjoiYXJtZWQuYW0iLCJzZXNzaW9uX29ubHkiOjEsImV4cCI6MTY0MzAyMDk5N30.pKm3OXcpUE8iVuUb4u4nGD-fdt3tc2aVyvLpnQv1sLA",
    //        "social_card": "5506920024",
    //        "mobile_number": "+37477642517",
    //        "email": "annapetrosyan92@mail.ru",
    //        "long_token":

}