package com.task.yogahaz.data.remote

import CategoriesResponseDto
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse
import com.task.yogahaz.utils.APIS
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface HomeApi {

    @GET(APIS.POPULAR)
    suspend fun getPopularSellers(): PopularSellersResponseDto

    @GET(APIS.TRENDING)
    suspend fun getTrendingSellers(): PopularSellersResponseDto

    @GET(APIS.CATEGORIES)
    suspend fun getCategories(): CategoriesResponseDto

    @POST(APIS.ADD_TO_FAVORITE)
    suspend fun addToFavorite(@Body body: AddToFavoriteBody): AddToFavoriteResponse
}