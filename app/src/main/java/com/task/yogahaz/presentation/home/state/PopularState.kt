package com.task.yogahaz.presentation.home.state

import com.task.yogahaz.domain.models.home.PopularSellersResponse

data class PopularState(
    val isLoading: Boolean = false,
    val popularResponse: PopularSellersResponse? = null,
    val error: String = ""
)