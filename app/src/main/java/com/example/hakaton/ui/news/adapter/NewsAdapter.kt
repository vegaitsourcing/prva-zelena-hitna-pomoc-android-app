package com.example.hakaton.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.models.news.News
import com.example.hakaton.R
import com.example.hakaton.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)
    val originalList = mutableListOf<News>()

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((News) -> Unit)? = null

    fun setOnItemClickListener(listener: (News) -> Unit) {
        onItemClickListener = listener
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(news: News) {
            binding.apply {
                textNewsDate.text = news.date
                textNewsTitle.text = news.title
                textNewsBody.text = news.description
                btnReadMore.setOnClickListener {
                    onItemClickListener?.let { it(news) }
                }

                Glide.with(imageNews)
                    .load(news.image)
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(imageNews)
            }
        }
    }

    fun filterList(filter: String) {
        val filteredList = originalList.filter { news ->
            news.title.lowercase().contains(filter.lowercase(), ignoreCase = true) or
                    news.description.lowercase()
                        .contains(filter.lowercase(), ignoreCase = true)
        }
        if (filter.isEmpty()) {
            differ.submitList(originalList)
        }
        differ.submitList(filteredList)
    }
}