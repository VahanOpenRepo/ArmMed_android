package com.armed.am.base

import androidx.preference.PreferenceManager
import com.armed.am.authorization.network.service.LoginApiService
import com.armed.am.authorization.presentation.LoginViewModel
import com.armed.am.authorization.repository.LoginRepository
import com.armed.am.patients.network.pagingSource.PatientsListPagingSource
import com.armed.am.patients.network.service.AddVisitApiService
import com.armed.am.patients.network.service.GetPersonSuggestionApiService
import com.armed.am.patients.network.service.PatientsListApiService
import com.armed.am.patients.presentation.AddVisitViewModel
import com.armed.am.patients.presentation.PatientsViewModel
import com.armed.am.patients.repository.AddVisitRepository
import com.armed.am.patients.repository.PatientsRepository
import com.armed.am.setup.network.di.NetworkModule
import com.armed.am.setup.network.interceptors.AuthorizationInterceptor
import com.armed.am.survey.network.service.QuestionsApiService
import com.armed.am.survey.presentation.SurveyViewModel
import com.armed.am.survey.repository.SurveyRepository
import com.armed.am.utils.Preferences
import com.armed.am.setup.network.utils.NetworkStatusViewModel
import org.json.JSONObject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.factory
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

val pagingSourcesModule = module {
    factory {(query: JSONObject)-> PatientsListPagingSource(get(),get(),query)}
}

val repositoriesModule = module {
    factory<LoginRepository>()
    factory<PatientsRepository>()
    factory<SurveyRepository>()
    factory<AddVisitRepository>()
}

val viewModelsModule = module {
    viewModel<LoginViewModel>()
    viewModel<PatientsViewModel>()
    viewModel<SurveyViewModel>()
    viewModel<NetworkStatusViewModel>()
    viewModel<AddVisitViewModel>()
}

val servicesModule = module {
    single<AuthorizationInterceptor>()
    single { Preferences(PreferenceManager.getDefaultSharedPreferences(get()), get()) }
    factory {
        get<Retrofit>(named(NetworkModule.RetrofitType.RETROFIT_NO_AUTH.value)).create(
            LoginApiService::class.java
        )
    }
    factory {
        get<Retrofit>(named(NetworkModule.RetrofitType.RETROFIT_WITH_AUTH.value)).create(
            PatientsListApiService::class.java
        )
    }
    factory {
        get<Retrofit>(named(NetworkModule.RetrofitType.RETROFIT_WITH_AUTH.value)).create(
            QuestionsApiService::class.java
        )
    }
    factory {
        get<Retrofit>(named(NetworkModule.RetrofitType.RETROFIT_WITH_AUTH.value)).create(
            AddVisitApiService::class.java
        )
    }
    factory {
        get<Retrofit>(named(NetworkModule.RetrofitType.RETROFIT_WITH_AUTH.value)).create(
            GetPersonSuggestionApiService::class.java
        )
    }
}