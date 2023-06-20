package com.arrazyfathan.nytimes.core.di

import android.content.Context
import com.arrazyfathan.nytimes.BuildConfig
import com.arrazyfathan.nytimes.core.data.source.remote.network.LoggingInterceptor
import com.arrazyfathan.nytimes.core.data.source.remote.network.NetworkInterceptor
import com.arrazyfathan.nytimes.core.data.source.remote.network.TopStoriesAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(LoggingInterceptor())
        logger.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logger
    }

    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .addInterceptor(getLoggingInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideTopStoriesApi(client: OkHttpClient, gson: Gson): TopStoriesAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(TopStoriesAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit.create(TopStoriesAPI::class.java)
    }
}
