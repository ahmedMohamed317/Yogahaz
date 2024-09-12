package com.task.yogahaz.fakedatasource

import CategoriesResponseDto
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.data.remote.HomeApi
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse

class FakeHomeApi : HomeApi {
    override suspend fun getPopularSellers(): PopularSellersResponseDto {
        return PopularSellersResponseDto(success = true , responseCode = 200 , message = "success", data = null)
    }

    override suspend fun getTrendingSellers(): PopularSellersResponseDto {
        return PopularSellersResponseDto(success = true , responseCode = 200 , message = "success", data = null)
    }

    override suspend fun getCategories(): CategoriesResponseDto {
        return CategoriesResponseDto(success = true , responseCode = 200 , message = "success", data = null)
    }

    override suspend fun addToFavorite(body: AddToFavoriteBody): AddToFavoriteResponse {
        return AddToFavoriteResponse(success = true , responseCode = 200 , message = "success")
    }
}
