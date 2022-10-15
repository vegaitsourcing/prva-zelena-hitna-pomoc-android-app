package com.example.hakaton.ui.partners.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.models.partners.Partner
import com.example.hakaton.R
import com.example.hakaton.databinding.ItemPartnerBinding

class PartnersAdapter : RecyclerView.Adapter<PartnersAdapter.PartnersViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Partner>() {
        override fun areItemsTheSame(oldItem: Partner, newItem: Partner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Partner, newItem: Partner): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnersViewHolder {
        return PartnersViewHolder(ItemPartnerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PartnersViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((Partner) -> Unit)? = null

    fun setOnItemClickListener(listener: (Partner) -> Unit) {
        onItemClickListener = listener
    }

    inner class PartnersViewHolder(private val binding: ItemPartnerBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(partner: Partner) {
            binding.apply {
                textPartnerCity.text = partner.city
                textPartnerName.text = partner.name

                btnShowPartnerDetails.setOnClickListener {
                    onItemClickListener?.let { it(partner) }
                }

                Glide.with(imagePartnerLogo)
                    .load(partner.logo)
                    .placeholder(R.drawable.ic_thumb)
                    .centerCrop()
                    .into(imagePartnerLogo)
            }
        }
    }
}