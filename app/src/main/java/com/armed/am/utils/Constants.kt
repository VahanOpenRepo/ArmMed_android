package com.armed.am.utils

/**
 * Created by Levon Arzumanyan on 10/11/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

object Constants {
    const val AGE = "Տարիք"
    const val GENDER = "Սեռ"
    const val PATTERN_yyyyMMdd_MINUSE = "yyyy-MM-dd"
    const val PATTERN_ddMMyyyy_DOT = "dd.MM.yyyy"
    const val PATTERN_yyyyMMdd_HHmmss_MINUSE_COLON = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_HHmm_COLON = "HH:mm"
    const val QUERY = "Query"
    const val FILTER = "Filter"
    const val FORMAT_JSON = "json"

    //TODO Change error constants to sealed class
    const val VALIDATION_SUCCESS = 1111
    const val REQUIRED_FIELD_IS_EMPTY = 1234
    const val START_DATE_IS_GREATER_THEN_END_DATE = 1235
    const val FUTURE_DATE_IS_SELECTED = 1236
    const val NO_RESULTS_FOUND = 1237

}