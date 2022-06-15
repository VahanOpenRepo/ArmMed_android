package com.armed.am.setup.network.utils

import com.armed.am.patients.network.model.AddVisitResponseModel
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AddVisitTypeConverter : JsonDeserializer<AddVisitResponseModel> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    )=let {
        val returnValue = AddVisitResponseModel()
        val result = AddVisitResponseModel.Result()
        val error = AddVisitResponseModel.Error()

        if (json?.asJsonObject?.get("result")?.isJsonObject == true) {
            returnValue.result=result
        } else{
            val jsonError = json?.asJsonObject?.get("error")?.asJsonObject
            error.apply {
                customErrorMessage = jsonError?.get("customErrorMessage")?.asString?:""
                errorCode = jsonError?.get("errorCode")?.asString?:""
                errorMessage = jsonError?.get("errorMessage")?.asString?:""
                errorType = jsonError?.get("errorType")?.asInt?:0
            }
            returnValue.error=error
        }

        returnValue
    }
}
