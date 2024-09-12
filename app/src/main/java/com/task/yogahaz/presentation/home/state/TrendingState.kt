package com.task.yogahaz.presentation.home.state

import com.task.yogahaz.domain.models.home.PopularSellersResponse

data class TrendingState(
    val isLoading: Boolean = false,
    val trendingResponse: PopularSellersResponse? = null,
    val error: String = ""
)