package com.task.yogahaz.domain.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.data.dto.home.response.CategoryDto
import com.task.yogahaz.data.dto.home.response.InformationDto
import com.task.yogahaz.data.dto.home.response.ProductCategoryDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryData(
    val name: String?,
    val image: String?,
    val nameEn: String?
) : Parcelable
