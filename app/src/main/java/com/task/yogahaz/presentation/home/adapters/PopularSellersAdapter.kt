package com.task.yogahaz.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.task.yogahaz.R
import com.task.yogahaz.base.BaseViewHolder
import com.task.yogahaz.base.DiffCallback
import com.task.yogahaz.databinding.ItemPopularNowHomeBinding
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.Restaurant
import com.task.yogahaz.utils.Utils
import kotlin.reflect.KFunction2

class PopularSellersAdapter(
    val addToFavorite: KFunction2<AddToFavoriteBody, ImageView, Unit>,

    ) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, DiffCallback<Restaurant>())


    fun submitList(list: List<Restaurant>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemPopularNowHomeBinding: ItemPopularNowHomeBinding =
            ItemPopularNowHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return ItemPopularNowHomeViewHolder(itemPopularNowHomeBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val restaurant = differ.currentList[position]
        (holder as ItemPopularNowHomeViewHolder).bind(restaurant)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemPopularNowHomeViewHolder(
        private val binding: ItemPopularNowHomeBinding,
    ) :
        BaseViewHolder<Restaurant>(binding) {
        override fun bind(item: Restaurant) {
            Utils.loadImage(binding.root.context ,
                    item.logo?: "" ,
                    binding.productImageIv,
                    R.drawable.product_placeholder,
                    19)
            binding.productNameTv.text = item.name
            binding.ratingBar.rating = item.rate?.toFloat() ?: 0f
            binding.distanceTv.text = item.distance ?: "Un known"
            binding.favoriteIv.setOnClickListener {
                addToFavorite(AddToFavoriteBody(item.id?:0),binding.favoriteIv)
            }
        }
    }

}
