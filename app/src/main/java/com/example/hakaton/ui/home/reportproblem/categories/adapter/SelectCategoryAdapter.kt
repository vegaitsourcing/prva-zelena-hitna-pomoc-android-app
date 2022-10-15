package com.example.hakaton.ui.home.reportproblem.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.common.models.home.categories.Category
import com.example.hakaton.databinding.ItemSelectCategoryBinding

class SelectCategoryAdapter : RecyclerView.Adapter<SelectCategoryAdapter.CategoryViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemSelectCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    inner class CategoryViewHolder(private val binding: ItemSelectCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(category: Category) {
            binding.apply {
                textCategoryName.text = category.name
                //imageCategory.setImageURI(category.image)
                root.setOnClickListener {
                    onItemClickListener?.let { it(category) }
                }
            }
        }
    }
}