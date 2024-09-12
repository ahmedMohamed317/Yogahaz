package com.task.yogahaz.domain.models.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Int?,
    val address: String?,
    val street: String?,
    val building: String?,
    val apartment: String?,
    val floor: String?,
    val name: String?
) : Parcelable
