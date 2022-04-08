package com.arrazyfathan.nytimes.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.nytimes.NYTimesApp
import com.arrazyfathan.nytimes.data.model.Article
import com.arrazyfathan.nytimes.data.model.TopStories
import com.arrazyfathan.nytimes.repository.NewsRepository
import com.arrazyfathan.nytimes.utils.Resources
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    val topStories: MutableLiveData<Resources<TopStories>> = MutableLiveData()
    var topStoriesResponse: TopStories? = null

    init {
        getTopStories()
    }

    fun getTopStories() = viewModelScope.launch {
        safeTopStoriesCall()
    }

    private suspend fun safeTopStoriesCall() {
        topStories.postValue(Resources.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getTopStories()
                topStories.postValue(handlingResponse(response))
            } else {
                topStories.postValue(Resources.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> topStories.postValue(Resources.Error("Network Failure"))
                else -> topStories.postValue(Resources.Error("Error"))
            }
        }
    }

    private fun handlingResponse(response: Response<TopStories>): Resources<TopStories>? {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (topStoriesResponse == null) {
                    topStoriesResponse = result
                }
                return Resources.Success(topStoriesResponse ?: result)
            }
        }
        return Resources.Error(response.message())
    }

    /*
    * Room
    * */
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun getSavedArticle() = newsRepository.getSavedArticle()

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NYTimesApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}

