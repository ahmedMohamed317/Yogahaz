package com.task.yogahaz.domain.models.home

import CategoryDataDto
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesResponse(
    val success: Boolean?,
    val responseCode: Int?,
    val message: String?,
    val data: List<CategoryData>?
) : Parcelable