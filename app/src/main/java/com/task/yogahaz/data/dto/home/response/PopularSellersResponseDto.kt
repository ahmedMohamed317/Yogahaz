package com.task.yogahaz.data.dto.home.response

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import com.task.yogahaz.domain.models.home.CategoriesResponse
import com.task.yogahaz.domain.models.home.CategoryData
import com.task.yogahaz.domain.models.home.PopularSellersResponse
import com.task.yogahaz.domain.models.home.Restaurant
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularSellersResponseDto(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("response_code") val responseCode: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: List<RestaurantDto>?
) : Parcelable

@Parcelize
data class RestaurantDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("logo") val logo: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("distance") val distance: String? = null,
    @SerializedName("type") val type: Int? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("lat") val lat: String? = null,
    @SerializedName("lng") val lng: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("appointments") val appointments: String? = null,
    @SerializedName("trending") val trending: Int? = null,
    @SerializedName("popular") val popular: Int? = null,
    @SerializedName("rate") val rate: String? = null,
    @SerializedName("is_favorite") val isFavorite: Boolean? = null,
    @SerializedName("categories") val categories: List<CategoryDto>? = null,
    @SerializedName("token") val token: String? = null,
    @SerializedName("information") val information: InformationDto? = null,
    @SerializedName("product_categories") val productCategories: List<ProductCategoryDto>? = null
) : Parcelable


@Parcelize
data class CategoryDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("active") val active: Int?
) : Parcelable

@Parcelize
data class ProductCategoryDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("active") val active: Int?,
    @SerializedName("name_ar") val nameAr: String?,
    @SerializedName("name_en") val nameEn: String?
) : Parcelable

@Parcelize
data class InformationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("identity_number") val identityNumber: String?,
    @SerializedName("tax_record") val taxRecord: String?,
    @SerializedName("activity") val activity: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("vehicle_image") val vehicleImage: String?,
    @SerializedName("vehicle_tablet_image") val vehicleTabletImage: String?,
    @SerializedName("vehicle_registration") val vehicleRegistration: String?,
    @SerializedName("driving_image") val drivingImage: String?
) : Parcelable

fun PopularSellersResponseDto.toPopularSellersResponse(): PopularSellersResponse {
    return PopularSellersResponse(
        success = success ,
        responseCode = responseCode,
        message = message,
        data = data?.map { it.toRestaurant() }
    )
}

fun RestaurantDto.toRestaurant(): Restaurant {
    return Restaurant(
        name = name,
        id = id,
        logo = logo,
        distance = distance,
        rate = rate,
        isFavorite = isFavorite,
        token = token,
    )
}