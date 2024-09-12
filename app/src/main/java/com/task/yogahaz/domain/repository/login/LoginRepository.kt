package com.task.yogahaz.domain.repository.login

import LoginResponseDto
import com.task.yogahaz.data.dto.login.request.LoginBody


interface LoginRepository {
    suspend fun login( body: LoginBody): LoginResponseDto
}