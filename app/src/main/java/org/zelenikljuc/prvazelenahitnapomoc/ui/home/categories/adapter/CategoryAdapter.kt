package org.zelenikljuc.prvazelenahitnapomoc.ui.home.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.zelenikljuc.common.models.home.categories.Subcategory
import org.zelenikljuc.prvazelenahitnapomoc.databinding.ItemSubcategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.SubcategoryViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Subcategory>() {
        override fun areItemsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        return SubcategoryViewHolder(ItemSubcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((Subcategory) -> Unit)? = null

    fun setOnItemClickListener(listener: (Subcategory) -> Unit) {
        onItemClickListener = listener
    }

    inner class SubcategoryViewHolder(private val binding: ItemSubcategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(subcategory: Subcategory) {
            binding.apply {
                textSubcategoryName.text = subcategory.name
                btnSubcategory.setOnClickListener {
                    onItemClickListener?.let { it(subcategory) }
                }
            }
        }
    }
}