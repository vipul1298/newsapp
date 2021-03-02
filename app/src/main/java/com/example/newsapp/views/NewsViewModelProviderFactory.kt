package com.example.newsapp.views

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.NewsApplication
import com.example.newsapp.repository.NewsRepository

// Used for passing the parameters in ViewModel constructor
class NewsViewModelProviderFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}