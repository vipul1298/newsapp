package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.models.SourceModel
import com.example.newsapp.models.SourceX
import kotlinx.android.synthetic.main.item_source.view.*

class SourceAdapter : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {
    inner class SourceViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    //diffUtil for better performance of recyclerview
    private val differCallback = object : DiffUtil.ItemCallback<SourceX>(){
        override fun areItemsTheSame(oldItem: SourceX, newItem: SourceX): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SourceX, newItem: SourceX): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        return SourceViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_source,
                parent,
                false

        ))
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
      val source = differ.currentList[position]
        holder.itemView.apply {
            source_name.text = source.name
            source_description.text = source.description
            source_country.text ="Country: ${source.country}"

            setOnClickListener {
                onItemClickListener?.let { it(source) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((SourceX) -> Unit)? = null

    // on click implementation
    fun setOnItemClickListener(listener: (SourceX) -> Unit){
        onItemClickListener = listener
    }
}