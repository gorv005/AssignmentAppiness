package com.appiness.assignmentappiness.modules

import com.appiness.assignmentappiness.BuildConfig
import com.appiness.assignmentappiness.interfaces.RESTApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor


object NetworkModule {

    val networkModule = module {

        single(named(OKHTTP)) { provideDefaultOkhttpClient() }
        single(named(RETROFIT)) { provideRetrofit(get(named(OKHTTP))) }
        single(named(RETROFIT_API)) { provideTmdbService(get(named(RETROFIT))) }

    }
    private const val WaitTime :Long= 3000
    private const val ConnTime :Long= 1000

    fun provideDefaultOkhttpClient(): OkHttpClient {
        
        return if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .callTimeout(WaitTime, TimeUnit.SECONDS)
                .connectTimeout(ConnTime, TimeUnit.SECONDS)
                .readTimeout(WaitTime, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .callTimeout(WaitTime, TimeUnit.SECONDS)
                .connectTimeout(ConnTime, TimeUnit.SECONDS)
                .readTimeout(WaitTime, TimeUnit.SECONDS)
                .build()
        }


    }

    private fun gson() = GsonBuilder()
        .create()

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    fun provideTmdbService(retrofit: Retrofit): RESTApi = retrofit.create(RESTApi::class.java)

    private const val OKHTTP = "okHttp"
    const val RETROFIT = "retrofit"
    const val RETROFIT_API = "retrofit_api"

}