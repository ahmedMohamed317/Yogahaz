package com.task.yogahaz.domain.repository.home

import CategoriesResponseDto
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse


interface HomeRepository {

    suspend fun getPopularSellers(): PopularSellersResponseDto

    suspend fun getTrendingSellers(): PopularSellersResponseDto

    suspend fun getCategories(): CategoriesResponseDto
    suspend fun addToFavorite(body: AddToFavoriteBody): AddToFavoriteResponse
}