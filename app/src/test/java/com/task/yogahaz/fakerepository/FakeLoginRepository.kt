package com.task.yogahaz.fakerepository

import LoginResponseDto
import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.domain.repository.login.LoginRepository
import java.io.IOException

class FakeLoginRepository : LoginRepository {

    private var shouldReturnError = false
    private lateinit var fakeLoginResponse: LoginResponseDto


    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setFakeLoginsResponse(response: LoginResponseDto) {
        fakeLoginResponse = response
    }

    override suspend fun login(body: LoginBody): LoginResponseDto {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakeLoginResponse
        }
    }


}
