package com.arrazyfathan.nytimes.repository

import com.arrazyfathan.nytimes.api.network.RetrofitInstance

class NewsRepository {

    suspend fun getTopStories() = RetrofitInstance.api.getTopStories()

}