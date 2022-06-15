package com.armed.am.utils

import com.armed.am.authorization.network.model.LoginResponseModel
import com.armed.am.survey.network.model.AnswerParamsModel
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object GsonUtils : KoinComponent {
    private val gson: Gson by inject()

    fun profileModelToJson(profileUIModel: LoginResponseModel.Result) = gson.toJson(profileUIModel)

    fun String?.getProfileModelFromJson() =
       try {
           gson.fromJson(this, LoginResponseModel.Result::class.java)
       } catch (e: Exception){
           null
       }

    fun JSONArray.removeLastItem() =
        if (this.length() != 0) {
            this.remove(this.length() - 1)
            1
        } else -1

    fun JSONArray.removeItemsFromEnd(itemsCountToRemove: Int) =
        if (itemsCountToRemove < this.length()) {
            for (i in 1..itemsCountToRemove) {
                this.remove(this.length() - i)
            }
            1
        } else -1

    //Helpers for survey
    private fun createQuestionsItemWithParams(question: String, name: String, value: String) =
        JSONObject().put("question", question)
            .put(
                "variables", JSONArray().put(
                    JSONObject()
                        .put("name", name)
                        .put("value", value)
                )
            )

    fun JSONArray.addQuestionItemWithParams(answerParamsModel: AnswerParamsModel): JSONArray {

        val questionJson = createQuestionsItemWithParams(
            answerParamsModel.question,
            answerParamsModel.name,
            answerParamsModel.value
        )
        when {
            this.length() == 0 -> {
                this.put(questionJson)
            }
            this[this.length() - 1].toString().substring(0, 40) == questionJson.toString()
                .substring(0, 40) -> {
                this.apply {
                    removeLastItem()
                    this.put(questionJson)
                }
            }
            else -> {
                this.put(questionJson)
            }
        }

        return this
    }

}