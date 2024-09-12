package com.task.yogahaz.data.repository.login

import LoginResponseDto
import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.data.remote.AuthApi
import com.task.yogahaz.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : LoginRepository {

    override suspend fun login(body: LoginBody): LoginResponseDto {
        return api.login(body)

    }

}