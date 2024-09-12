package com.task.yogahaz.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.task.yogahaz.R
import com.task.yogahaz.utils.base.BaseViewHolder
import com.task.yogahaz.utils.base.DiffCallback
import com.task.yogahaz.databinding.ItemCategoriesHomeBinding
import com.task.yogahaz.domain.models.home.CategoryData
import com.task.yogahaz.utils.Utils

class CategoriesAdapter :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, DiffCallback<CategoryData>())


    fun submitList(list: List<CategoryData>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemCategoriesBinding: ItemCategoriesHomeBinding =
            ItemCategoriesHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ItemExploreCategoriesViewHolder(itemCategoriesBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val category = differ.currentList[position]
        (holder as ItemExploreCategoriesViewHolder).bind(category)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemExploreCategoriesViewHolder(
        private val binding: ItemCategoriesHomeBinding,
    ) :
        BaseViewHolder<CategoryData>(binding) {
        override fun bind(item: CategoryData) {
            Utils.loadImage(binding.root.context ,
                    item.image?: "" ,
                    binding.productImageIv,
                    R.drawable.product_placeholder,
                    1)
            binding.productNameTv.text = item.nameEn
        }
    }

}
