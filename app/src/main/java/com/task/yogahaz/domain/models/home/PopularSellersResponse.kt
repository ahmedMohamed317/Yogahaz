package com.task.yogahaz.domain.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.data.dto.home.response.RestaurantDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularSellersResponse(
    val success: Boolean?,
    val responseCode: Int?,
    val message: String?,
    val data: List<Restaurant>?
) : Parcelable