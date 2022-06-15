package com.armed.am.survey.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.armed.am.base.BaseViewModel
import com.armed.am.setup.network.helpers.QResult
import com.armed.am.survey.network.model.AnswerParamsModel
import com.armed.am.survey.network.model.QuestionParamsModel
import com.armed.am.survey.repository.SurveyRepository
import com.armed.am.utils.GsonUtils.addQuestionItemWithParams
import com.armed.am.utils.GsonUtils.removeItemsFromEnd
import com.armed.am.utils.toImmutableLiveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray

/**
 * Created by Levon Arzumanyan on 10/10/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class SurveyViewModel(private val surveyRepository: SurveyRepository) : BaseViewModel() {
    private var questionsArray = JSONArray()
    var lastRequestedQuestionsCount = 1
    private val getQuestionsLiveData = MutableLiveData<QuestionParamsModel?>()

    fun getQuestions(questionParamsCount: Int = 1, vararg answerParams: AnswerParamsModel) {
        //adding all params to questions array
        if (answerParams.isNullOrEmpty().not()){
            answerParams.forEach {
                questionsArray.addQuestionItemWithParams(it)
            }
        }

        //updating last questionsNumber to handle back case
        lastRequestedQuestionsCount = questionParamsCount

        //requesting the questions with given array
        viewModelScope.launch {
            surveyRepository.getQuestions(questionsArray).collect {
                when (it) {
                    is QResult.Loading -> isLoadingLiveData.value = true
                    is QResult.Fail -> {
                        errorMessageLiveData.value = it.error.message
                        isLoadingLiveData.value = false
                    }
                    is QResult.Success -> {
                        getQuestionsLiveData.value = it.value
                        isLoadingLiveData.value = false
                    }
                }
            }
        }
    }

    fun removeItemsFromEndOfQuestionsArray(itemsCountToRemove:Int){
        questionsArray.removeItemsFromEnd(itemsCountToRemove)
    }

    fun getLengthOfQuestionArray() = questionsArray.length()

    fun clearQuestionsArray() {
        questionsArray= JSONArray()
    }

    fun getQuestionsLiveData() = getQuestionsLiveData.toImmutableLiveData()
    fun clearQuestionsLiveData()  {getQuestionsLiveData.value=null}
    fun getLoadingLiveData() = isLoadingLiveData.toImmutableLiveData()
    fun getErrorLiveData() = errorMessageLiveData.toImmutableLiveData()

}