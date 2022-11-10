package org.zelenikljuc.prvazelenahitnapomoc.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.prvazelenahitnapomoc.R
import org.zelenikljuc.prvazelenahitnapomoc.databinding.HomeCategoryItemBinding

class HomeCategoryAdapter : RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder>() {

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
        return CategoryViewHolder(HomeCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    private var onItemClickListener: ((Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (Category) -> Unit) {
        onItemClickListener = listener
    }

    inner class CategoryViewHolder(private val binding: HomeCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(category: Category) {
            binding.apply {
                textCategoryName.text = category.name

                Glide.with(imageCategory)
                    .load(category.image)
                    .placeholder(R.drawable.ic_thumb)
                    .into(imageCategory)

                root.setOnClickListener {
                    onItemClickListener?.let { it(category) }
                }
            }
        }
    }
}