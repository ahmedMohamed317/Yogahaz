package com.task.yogahaz.fakerepository

import CategoriesResponseDto
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse
import com.task.yogahaz.domain.repository.home.HomeRepository
import java.io.IOException

class FakeHomeRepository : HomeRepository {

    private var shouldReturnError = false
    private lateinit var fakePopularSellersResponse: PopularSellersResponseDto
    private lateinit var fakeTrendingSellersResponse: PopularSellersResponseDto
    private lateinit var fakeCategoriesResponse: CategoriesResponseDto
    private lateinit var fakeAddToFavoriteResponse: AddToFavoriteResponse

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setFakePopularSellersResponse(response: PopularSellersResponseDto) {
        fakePopularSellersResponse = response
    }

    fun setFakeTrendingSellersResponse(response: PopularSellersResponseDto) {
        fakeTrendingSellersResponse = response
    }

    fun setFakeCategoriesResponse(response: CategoriesResponseDto) {
        fakeCategoriesResponse = response
    }

    fun setFakeAddToFavoriteResponse(response: AddToFavoriteResponse) {
        fakeAddToFavoriteResponse = response
    }

    override suspend fun getPopularSellers(): PopularSellersResponseDto {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakePopularSellersResponse
        }
    }

    override suspend fun getTrendingSellers(): PopularSellersResponseDto {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakeTrendingSellersResponse
        }
    }

    override suspend fun getCategories(): CategoriesResponseDto {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakeCategoriesResponse
        }
    }

    override suspend fun addToFavorite(body: AddToFavoriteBody): AddToFavoriteResponse {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakeAddToFavoriteResponse
        }
    }
}
