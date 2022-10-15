package com.example.hakaton.ui.home.reportproblem.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hakaton.R
import com.example.hakaton.databinding.ItemMediaFileBinding

class MediaFilesAdapter : RecyclerView.Adapter<MediaFilesAdapter.MediaFileViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaFileViewHolder {
        return MediaFileViewHolder(ItemMediaFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MediaFileViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((Uri) -> Unit)? = null

    fun setOnItemClickListener(listener: (Uri) -> Unit) {
        onItemClickListener = listener
    }

    inner class MediaFileViewHolder(private val binding: ItemMediaFileBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(mediaFile: Uri) {
            binding.apply {
                Glide.with(imageMediaFile)
                    .load(mediaFile.toString())
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(imageMediaFile)


                imageMediaFile.setOnClickListener {
                    onItemClickListener?.let { it(mediaFile) }
                }
            }
        }
    }
}