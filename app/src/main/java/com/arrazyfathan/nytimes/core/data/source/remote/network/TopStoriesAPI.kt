package com.arrazyfathan.nytimes.core.data.source.remote.network

import com.arrazyfathan.nytimes.BuildConfig
import com.arrazyfathan.nytimes.core.data.source.remote.response.TopStoriesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */
interface TopStoriesAPI {

    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("topstories/v2/{section}.json")
    suspend fun getTopStories(
        @Path("section") section: String,
        @Query("api-key") apiKey: String = API_KEY,
    ): Response<TopStoriesDto>
}
