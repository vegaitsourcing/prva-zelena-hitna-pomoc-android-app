package com.example.hakaton.ui.home.wastedisposal.adater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.common.models.home.wastedisposal.WasteDisposalLocation
import com.example.hakaton.databinding.ItemWasteDisposalLocationBinding

class WasteDisposalAdapter : RecyclerView.Adapter<WasteDisposalAdapter.WasteDisposalLocationsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<WasteDisposalLocation>() {
        override fun areItemsTheSame(oldItem: WasteDisposalLocation, newItem: WasteDisposalLocation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WasteDisposalLocation, newItem: WasteDisposalLocation): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteDisposalLocationsViewHolder {
        return WasteDisposalLocationsViewHolder(ItemWasteDisposalLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WasteDisposalLocationsViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((WasteDisposalLocation) -> Unit)? = null

    fun setOnItemClickListener(listener: (WasteDisposalLocation) -> Unit) {
        onItemClickListener = listener
    }

    inner class WasteDisposalLocationsViewHolder(private val binding: ItemWasteDisposalLocationBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(location: WasteDisposalLocation) {
            binding.apply {
                textWasteDisposalLocationName.text = location.name
                btnWasteDisposalLocation.setOnClickListener {
                    onItemClickListener?.let { it(location) }
                }
            }
        }
    }
}