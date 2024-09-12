package com.task.yogahaz.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.task.yogahaz.R
import com.task.yogahaz.base.BaseViewHolder
import com.task.yogahaz.base.DiffCallback
import com.task.yogahaz.databinding.ItemTrendingHomeBinding
import com.task.yogahaz.domain.models.home.Restaurant
import com.task.yogahaz.utils.Utils

class TrendingAdapter() :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, DiffCallback<Restaurant>())


    fun submitList(list: List<Restaurant>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemCategoriesBinding: ItemTrendingHomeBinding =
            ItemTrendingHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ItemTrendingViewHolder(itemCategoriesBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val category = differ.currentList[position]
        (holder as ItemTrendingViewHolder).bind(category)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemTrendingViewHolder(
        private val binding: ItemTrendingHomeBinding,
    ) :
        BaseViewHolder<Restaurant>(binding) {
        override fun bind(item: Restaurant) {
            Utils.loadImage(binding.root.context ,
                    item.logo?: "" ,
                    binding.productImageIv,
                    R.drawable.product_placeholder,
                    1)
        }
    }

}
