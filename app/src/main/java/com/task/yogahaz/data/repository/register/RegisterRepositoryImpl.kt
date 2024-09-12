package com.task.yogahaz.data.repository.register

import RegisterResponseDto
import android.util.Log
import com.task.yogahaz.data.dto.register.request.RegisterBody
import com.task.yogahaz.data.remote.AuthApi
import com.task.yogahaz.domain.repository.register.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : RegisterRepository {

    override suspend fun register(body: RegisterBody): RegisterResponseDto{
        return api.register(body)

    }

}