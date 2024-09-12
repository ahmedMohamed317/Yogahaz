package com.task.yogahaz.presentation.home.state

import com.task.yogahaz.domain.models.home.CategoriesResponse

data class CategoriesState(
    val isLoading: Boolean = false,
    val categoriesResponse: CategoriesResponse? = null,
    val error: String = ""
)