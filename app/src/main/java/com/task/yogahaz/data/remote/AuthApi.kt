package com.task.yogahaz.data.remote


import LoginResponseDto
import RegisterResponseDto
import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.data.dto.register.request.RegisterBody
import com.task.yogahaz.utils.constants.APIS
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(APIS.LOGIN)
    suspend fun login(@Body body: LoginBody): LoginResponseDto

    @POST(APIS.REGISTER)
    suspend fun register(@Body body: RegisterBody): RegisterResponseDto

}