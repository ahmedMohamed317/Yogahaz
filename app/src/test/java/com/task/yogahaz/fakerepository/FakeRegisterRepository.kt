package com.task.yogahaz.fakerepository

import RegisterResponseDto
import com.task.yogahaz.data.dto.register.request.RegisterBody
import com.task.yogahaz.domain.repository.register.RegisterRepository
import java.io.IOException

class FakeRegisterRepository : RegisterRepository {

    private var shouldReturnError = false
    private lateinit var fakeRegisterResponse: RegisterResponseDto


    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun setFakeRegisterResponse(response: RegisterResponseDto) {
        fakeRegisterResponse = response
    }

    override suspend fun register(body: RegisterBody): RegisterResponseDto {
        return if (shouldReturnError) {
            throw IOException("Couldn't reach server. Check your internet connection.")
        } else {
            fakeRegisterResponse
        }
    }


}
