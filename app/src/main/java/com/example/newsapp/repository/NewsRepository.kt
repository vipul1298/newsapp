package com.example.newsapp.repository

import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.service.RetrofitInstance

// datasource repo which collects all the data here to use in Viewmodel

class NewsRepository(
        val db: ArticleDatabase
) {
    suspend fun getTopHeadlines(countryCode:String,pageNumber:Int) =
            RetrofitInstance.api.getTopHeadlines(countryCode,pageNumber)

    suspend fun getSources(pageNumber: Int) = RetrofitInstance.api.getSources(pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber: Int) =
            RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
}