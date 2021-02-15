package com.example.newsapp.views

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import kotlinx.android.synthetic.main.fragment_source_detail.*

class SourceDetailFragment : Fragment(R.layout.fragment_source_detail) {

    val args : SourceDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val source = args.source
        sWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(source.url)

        }

    }


}