package com.example.newsapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.newsapp.NewsApplication
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.service.RetrofitInstance

// datasource repo which collects all the data here to use in Viewmodel

class NewsRepository(
        val db: ArticleDatabase,
        val context: Context

) {
    suspend fun getTopHeadlines(countryCode:String,pageNumber:Int) =
            RetrofitInstance.api.getTopHeadlines(countryCode,pageNumber)

    suspend fun getSources(pageNumber: Int) = RetrofitInstance.api.getSources(pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber: Int) =
            RetrofitInstance.api.searchForNews(searchQuery,pageNumber)


    //Network Connectivity

     fun hasInternetConnection() :Boolean {
        val connectivityManager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
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