package com.task.yogahaz.presentation.home.state

import com.task.yogahaz.domain.models.home.AddToFavoriteResponse

data class AddToFavoriteState(
    val isLoading: Boolean = false,
    val addToFavoriteResponse: AddToFavoriteResponse? = null,
    val error: String = ""
)