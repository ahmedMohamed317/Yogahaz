package com.task.yogahaz.domain.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddToFavoriteResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("response_code") val responseCode: Int?,
    @SerializedName("message") val message: String?,
) : Parcelable

@Parcelize
data class AddToFavoriteBody(
    @SerializedName("user_id") val userId: Int
) : Parcelable