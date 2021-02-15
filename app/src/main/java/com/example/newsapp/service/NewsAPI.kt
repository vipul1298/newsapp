package com.example.newsapp.service

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.models.SourceModel
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//* http methods using Retrofit *//

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
            @Query("country")
            countryCode: String = "us",
            @Query("page")
            pageNumber : Int = 1,
            @Query("apiKey")
            apiKey : String = API_KEY

    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
            @Query("q")
            searchQuery: String,
            @Query("page")
            pageNumber : Int = 1,
            @Query("apiKey")
            apiKey : String = API_KEY

    ) : Response<NewsResponse>

    @GET("v2/sources")
    suspend fun getSources(

            @Query("page")
            pageNumber : Int = 1,
            @Query("apiKey")
            apiKey : String = API_KEY

    ) : Response<SourceModel>
}