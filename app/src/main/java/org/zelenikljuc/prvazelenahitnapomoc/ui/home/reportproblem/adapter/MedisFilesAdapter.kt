package org.zelenikljuc.prvazelenahitnapomoc.ui.home.reportproblem.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.ItemMediaFileBinding

class MediaFilesAdapter(private var images: ArrayList<Uri>, private var iOnMediaFileClickListener: IOnMediaFileClickListener)
    : RecyclerView.Adapter<MediaFilesAdapter.MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemMediaFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.setData(images[position], iOnMediaFileClickListener)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class MediaViewHolder(private val binding: ItemMediaFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(uri: Uri, iOnMediaFileClickListener: IOnMediaFileClickListener) {
            binding.apply {
                Glide.with(imageMediaFile)
                    .load(uri.toString())
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(imageMediaFile)

                root.setOnClickListener {
                    iOnMediaFileClickListener.onMediaFileClick(adapterPosition, uri)
                }
            }
        }
    }
}