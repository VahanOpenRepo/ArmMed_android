package com.armed.am.survey.repository

import com.armed.am.setup.network.helpers.QResult
import com.armed.am.survey.network.model.QuestionParamsModel
import com.armed.am.survey.network.service.QuestionsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray

/**
 * Created by Levon Arzumanyan on 10/10/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class SurveyRepository(
    private val questionsApiService: QuestionsApiService,
) {
    fun getQuestions(questionsArray: JSONArray): Flow<QResult<QuestionParamsModel>> =
        flow {
            emit(QResult.Loading)

            val result = try {
                val response = questionsApiService.getQuestions(questionsArray)
                QResult.Success(response.result)
            } catch (e: Exception) {
                QResult.Fail(e)
            }

            emit(result)
        }
}