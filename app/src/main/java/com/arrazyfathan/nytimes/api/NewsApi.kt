package com.arrazyfathan.nytimes.api

import com.arrazyfathan.nytimes.BuildConfig
import com.arrazyfathan.nytimes.data.model.TopStories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
        const val API_KEY = BuildConfig.NEWS_API_KEY
    }

    @GET("/home.json")
    suspend fun getTopStories(
        @Query("api-key") apiKey: String = API_KEY
    ): Response<TopStories>


}