package com.example.pridestarappschallenge.network.helpers.modules

import com.example.pridestarappschallenge.network.helpers.helpers.UrlBuilder
import com.example.pridestarappschallenge.network.helpers.services.StoriesAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule{

    //Create Mock Http Client that can be injected with Hilt
    @Provides
    @Singleton
    fun getMockOkHttpClient(): OkHttpClient {

        val httpBuilder = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.interceptors()
            .add(httpLoggingInterceptor)

        return httpBuilder.build()
    }

    //Create Mock Retrofit that can be injected with Hilt
    @Provides
    @Singleton
    fun getMockRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(UrlBuilder.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    //Create API service that can be injected with Hilt
    @Provides
    @Singleton
    fun getMockApiService(retrofit: Retrofit): StoriesAPIService =
        retrofit.create(StoriesAPIService::class.java)
}