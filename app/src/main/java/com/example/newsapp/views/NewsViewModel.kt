package com.example.newsapp.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.NewsApplication

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.models.SourceModel
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

// viewmodel to handle the data source which will be reflected to Ui
class NewsViewModel(
    private val newsRepository : NewsRepository
) : ViewModel() {
    val topHeadlines : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val sources : MutableLiveData<Resource<SourceModel>> = MutableLiveData()
    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var topHeadlinesPage =1
    var searchNewsPage=1
    var sourcesPage=1

    init {
        getTopHeadlines("us") // currently for U.S.
        getSources()
    }

    fun getTopHeadlines(countryCode:String) = viewModelScope.launch {
        safeTopHeadlinesCall(countryCode)
    }

    fun getSources() = viewModelScope.launch {
        safeSourcesCall()
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }


    // handling responses

    private fun handleTopHeadlinewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSourcesResponse(response: Response<SourceModel>) : Resource<SourceModel>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeTopHeadlinesCall(countryCode: String){
        topHeadlines.postValue(Resource.Loading())
        try {
            if(newsRepository.hasInternetConnection()){
                val response =  newsRepository.getTopHeadlines(countryCode,topHeadlinesPage)
                topHeadlines.postValue(handleTopHeadlinewsResponse(response))
            }else{
                topHeadlines.postValue(Resource.Error("No Internet Connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> topHeadlines.postValue(Resource.Error("Network Failure"))
                else -> topHeadlines.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSourcesCall(){
        sources.postValue(Resource.Loading())
        try {
            if(newsRepository.hasInternetConnection()){
                val response =  newsRepository.getSources(sourcesPage)
                sources.postValue(handleSourcesResponse(response))
            }else{
                sources.postValue(Resource.Error("No Internet Connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> sources.postValue(Resource.Error("Network Failure"))
                else -> sources.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try {
            if(newsRepository.hasInternetConnection()){
                val response =  newsRepository.searchNews(searchQuery,searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }




}