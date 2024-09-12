package com.task.yogahaz.data.repository.home

import CategoriesResponseDto
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.data.remote.HomeApi
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse
import com.task.yogahaz.domain.repository.home.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : HomeRepository {
    override suspend fun getPopularSellers(): PopularSellersResponseDto {
        return api.getPopularSellers()
    }

    override suspend fun getTrendingSellers(): PopularSellersResponseDto {
        return api.getTrendingSellers()
    }

    override suspend fun getCategories(): CategoriesResponseDto {
        return api.getCategories()
    }

    override suspend fun addToFavorite(body: AddToFavoriteBody): AddToFavoriteResponse {
        return api.addToFavorite(body)
    }
}