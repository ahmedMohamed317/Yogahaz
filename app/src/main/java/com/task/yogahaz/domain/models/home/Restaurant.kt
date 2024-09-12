package com.task.yogahaz.domain.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.data.dto.home.response.CategoryDto
import com.task.yogahaz.data.dto.home.response.InformationDto
import com.task.yogahaz.data.dto.home.response.ProductCategoryDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: Int?,
    val name: String?,
    val logo: String?,
    val distance: String?,
    val rate: String?,
    val isFavorite: Boolean?,
    val token: String?,

) : Parcelable