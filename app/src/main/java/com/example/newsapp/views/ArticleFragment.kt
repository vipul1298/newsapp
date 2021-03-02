package com.example.newsapp.views

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import kotlinx.android.synthetic.main.fragment_article.*

// showing web url
class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

    }

//    fun openCustomTab(url:String){
//        val builder : CustomTabsIntent.Builder  = CustomTabsIntent.Builder()
//        val customTabsIntent : CustomTabsIntent = builder.build()
//        context?.let { customTabsIntent.launchUrl(it, Uri.parse(url)) }
//
//    }
}