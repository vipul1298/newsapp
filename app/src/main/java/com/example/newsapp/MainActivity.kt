package com.example.newsapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.views.NewsViewModel
import com.example.newsapp.views.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

// Container for all the fragments
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    lateinit  var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar =findViewById(R.id.toolBar)

        setSupportActionBar(toolbar) // collapsing toolbar



        val newsRepository = NewsRepository(ArticleDatabase(this),this)
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)


        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController()) // controller for bottom navigaton using navigation component
    }


}