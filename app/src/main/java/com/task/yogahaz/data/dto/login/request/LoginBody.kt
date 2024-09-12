package com.task.yogahaz.data.dto.login.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginBody(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("device_token") val deviceToken: String = "",
) : Parcelable