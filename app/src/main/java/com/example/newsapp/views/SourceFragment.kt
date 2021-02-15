package com.example.newsapp.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.SourceAdapter
import com.example.newsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_source.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import kotlinx.android.synthetic.main.fragment_top_headlines.paginationProgressBar

class SourceFragment : Fragment(R.layout.fragment_source) {
    lateinit var viewModel: NewsViewModel
    lateinit var sourceAdapter: SourceAdapter

    val TAG = "SourceFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        initRecyclerView()

        sourceAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("source",it)
            }

            findNavController().navigate(
                    R.id.action_sourceFragment_to_sourceDetailFragment
                    ,bundle
            )
        }

        viewModel.sources.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { sourceResponse ->
                        sourceAdapter.differ.submitList(sourceResponse.sources)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()

                }
            }

        })

    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }
    private fun initRecyclerView(){
        sourceAdapter = SourceAdapter()
        rvSources.apply {
            adapter=sourceAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}