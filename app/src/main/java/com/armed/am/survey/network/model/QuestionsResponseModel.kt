package com.armed.am.survey.network.model

/**
 * Created by Levon Arzumanyan on 10/10/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

data class QuestionsResponseModel(
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
    val result: QuestionParamsModel,
    val status: Int,
    val unique_id: Any,
    val user: String,
    val waiting_time: Any
) {
    data class Log(
        val logs: List<Any>
    )

}