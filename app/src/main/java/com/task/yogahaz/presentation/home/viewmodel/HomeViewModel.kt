package com.task.yogahaz.presentation.home.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.usecases.favorite.AddToFavoriteUseCase
import com.task.yogahaz.domain.usecases.home.GetCategoriesUseCase
import com.task.yogahaz.domain.usecases.home.GetPopularUseCase
import com.task.yogahaz.domain.usecases.home.GetTrendingUseCase
import com.task.yogahaz.presentation.home.state.AddToFavoriteState
import com.task.yogahaz.presentation.home.state.CategoriesState
import com.task.yogahaz.presentation.home.state.PopularState
import com.task.yogahaz.presentation.home.state.TrendingState
import com.task.yogahaz.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getPopularSellerUseCase: GetPopularUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase

) : ViewModel() {

    private val _getCategoriesState = MutableStateFlow(CategoriesState())
    val getCategoriesState: StateFlow<CategoriesState> = _getCategoriesState

    private val _getTrendingState = MutableStateFlow(TrendingState())
    val getTrendingState: StateFlow<TrendingState> = _getTrendingState

    private val _getPopularSellerState = MutableStateFlow(PopularState())
    val getPopularSellerState: StateFlow<PopularState> = _getPopularSellerState

    private val _addToFavoriteState = MutableStateFlow(AddToFavoriteState())
    val addToFavoriteState: StateFlow<AddToFavoriteState> = _addToFavoriteState

    init {
        getCategories()
        getTrendingSellers()
        getPopularSellers()
    }

    fun getCategories() {
        try {
            getCategoriesUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _getCategoriesState.value = CategoriesState(categoriesResponse = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        _getCategoriesState.value = CategoriesState(
                            error = result.message ?: "An unexpected error occurred", isLoading = false
                        )
                    }
                    is Result.Loading -> {
                        _getCategoriesState.value = CategoriesState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            _getCategoriesState.value = CategoriesState(error = e.message ?: "An unexpected error occurred")
        }
    }

    fun getTrendingSellers() {
        try {
            getTrendingUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _getTrendingState.value = TrendingState(trendingResponse = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        _getTrendingState.value = TrendingState(
                            error = result.message ?: "An unexpected error occurred", isLoading = false
                        )
                    }
                    is Result.Loading -> {
                        _getTrendingState.value = TrendingState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            _getTrendingState.value = TrendingState(error = e.message ?: "An unexpected error occurred")
        }
    }

    fun getPopularSellers() {
        try {
            getPopularSellerUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _getPopularSellerState.value = PopularState( popularResponse = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        _getPopularSellerState.value = PopularState(
                            error = result.message ?: "An unexpected error occurred", isLoading = false
                        )
                    }
                    is Result.Loading -> {
                        _getPopularSellerState.value = PopularState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            _getPopularSellerState.value = PopularState(error = e.message ?: "An unexpected error occurred")
        }
    }


    fun addToFavorite(body: AddToFavoriteBody) {
        try {
            addToFavoriteUseCase(body).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _addToFavoriteState.value = AddToFavoriteState( addToFavoriteResponse = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        _addToFavoriteState.value = AddToFavoriteState(
                            error = result.message ?: "An unexpected error occurred", isLoading = false
                        )
                    }
                    is Result.Loading -> {
                        _addToFavoriteState.value = AddToFavoriteState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            _addToFavoriteState.value = AddToFavoriteState(error = e.message ?: "An unexpected error occurred")
        }
    }

}
