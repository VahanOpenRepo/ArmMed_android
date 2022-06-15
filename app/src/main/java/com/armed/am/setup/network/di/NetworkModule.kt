package com.armed.am.setup.network.di

import com.armed.am.BuildConfig
import com.armed.am.patients.network.model.AddVisitResponseModel
import com.armed.am.setup.network.interceptors.AuthorizationInterceptor
import com.armed.am.setup.network.utils.NetworkStatusTracker
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.armed.am.setup.network.utils.AddVisitTypeConverter
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

object NetworkModule {

    enum class RetrofitType(val value: String) {
        RETROFIT_NO_AUTH("retrofit-no-auth"),
        RETROFIT_WITH_AUTH("retrofit-with-auth"),
    }

    enum class OkHttpType(val value: String) {
        OK_HTTP_DEFAULT("okhttp-default"),
        OK_HTTP_WITH_AUTH("okhttp-auth")
    }


    val networkModule = module {

        factory { NetworkStatusTracker(get()) }

        // Gson
        single {
            GsonBuilder()
                .registerTypeAdapter(AddVisitResponseModel::class.java, AddVisitTypeConverter())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .create()
        }

        single {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return@single loggingInterceptor
        }

        // http client
        single(named(OkHttpType.OK_HTTP_DEFAULT.value)) {
            createOkHttpClientDefault(
                httpLoggingInterceptor = get(),
            )
        }

        single(named(OkHttpType.OK_HTTP_WITH_AUTH.value)) {
            createAuthOkHttpClient(
                okHttpClient = get(named(OkHttpType.OK_HTTP_DEFAULT.value)),
                httpLoggingInterceptor = get(),
                authorizationInterceptor = get()
            )
        }

        // retrofit
        single(named(RetrofitType.RETROFIT_NO_AUTH.value)) {
            createRetrofit(
                okHttpClient = get(named(OkHttpType.OK_HTTP_DEFAULT.value)),
                baseUrl = BuildConfig.BASE_URL,
                get()
            )
        }

        single(named(RetrofitType.RETROFIT_WITH_AUTH.value)) {
            createRetrofit(
                okHttpClient = get(named(OkHttpType.OK_HTTP_WITH_AUTH.value)),
                baseUrl = BuildConfig.BASE_URL,
                get()
            )
        }

    }

    private fun createOkHttpClientDefault(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticator: Authenticator? = null
    ): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String,
                ) = Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String,
                ) = Unit

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> =
                    arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder().apply {
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                addInterceptor(httpLoggingInterceptor)
                hostnameVerifier { _, _ -> true }.apply {
                    if (authenticator != null) {
                        authenticator(authenticator)
                    }
                }
            }
            return builder
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES).build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createAuthOkHttpClient(
        okHttpClient: OkHttpClient,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ) = okHttpClient.newBuilder()
        .addInterceptor(authorizationInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        gson: Gson
    ) =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

}