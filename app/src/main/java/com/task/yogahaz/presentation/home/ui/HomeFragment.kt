package com.task.yogahaz.presentation.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.yogahaz.R
import com.task.yogahaz.utils.base.BaseFragment
import com.task.yogahaz.databinding.FragmentHomeBinding
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.CategoryData
import com.task.yogahaz.domain.models.home.Restaurant
import com.task.yogahaz.presentation.home.adapters.CategoriesAdapter
import com.task.yogahaz.presentation.home.adapters.PopularSellersAdapter
import com.task.yogahaz.presentation.home.adapters.TrendingAdapter
import com.task.yogahaz.presentation.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val args: HomeFragmentArgs by navArgs()
    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }
    private val popularSellersAdapter: PopularSellersAdapter by lazy {
        PopularSellersAdapter(
            addToFavorite = ::addToFavorite
        )
    }
    private val trendingSellersAdapter: TrendingAdapter by lazy {
        TrendingAdapter()
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    override fun onCreateInit() {
        initSetAdapter()
        initToolBar()
        setupViews()
        observeTrendingSellers()
        observeCategories()
        observePopularSellers()
    }

    private fun initSetAdapter() {
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvPopular.adapter = popularSellersAdapter
        binding.rvTrending.adapter = trendingSellersAdapter
    }

    private fun initToolBar() {
        binding.toolbar.tvTitle.text = resources.getString(R.string.home)
    }


    private fun updateCategories(categories: List<CategoryData>?) {
        categoriesAdapter.submitList(categories ?: emptyList())
    }


    private fun updateTrendingSellers(trendingSellers: List<Restaurant>?) {
        trendingSellersAdapter.submitList(trendingSellers ?: emptyList())
    }

    private fun updatePopularSellers(popularSellers: List<Restaurant>?) {
        popularSellersAdapter.submitList(popularSellers ?: emptyList())
    }

    private fun observeCategories() {
        homeViewModel.getCategoriesState.onEach { state ->
            when {
                state.isLoading -> {
                    showProgressBar()
                }
                state.categoriesResponse != null -> {
                    dismissProgressBar()
                    updateCategories(state.categoriesResponse.data)
                }
                state.error.isNotEmpty() -> {
                    dismissProgressBar()
                    showToast(state.error)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun observeTrendingSellers() {
        homeViewModel.getTrendingState.onEach { state ->
            when {

                state.trendingResponse != null -> {
                    updateTrendingSellers(state.trendingResponse.data)
                }
                state.error.isNotEmpty() -> {
                    showToast(state.error)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun observePopularSellers() {
        homeViewModel.getPopularSellerState.onEach { state ->
            when {
                state.popularResponse != null -> {
                    updatePopularSellers(state.popularResponse.data)
                }
                state.error.isNotEmpty() -> {
                    showToast(state.error)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun addToFavorite(body: AddToFavoriteBody,button :ImageView) {
        homeViewModel.addToFavorite(body)

        homeViewModel.addToFavoriteState.onEach { state ->
            when {
                state.isLoading -> {
                    showProgressBar()
                }
                state.addToFavoriteResponse != null -> {
                    dismissProgressBar()
                    button.setImageResource(R.drawable.fav_filled_icon)
                }
                state.error.isNotEmpty() -> {
                    dismissProgressBar()
                    showToast(state.error)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun setupViews(){
        binding.userNameTv.text = args.userName
        binding.addressTv.text = args.userAddress

    }
}
