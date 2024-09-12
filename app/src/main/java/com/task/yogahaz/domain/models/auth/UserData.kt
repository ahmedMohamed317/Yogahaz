package com.task.yogahaz.domain.models.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val image: String?,
    val token: String?,
    val addresses: List<Address>? ) : Parcelable