package com.task.yogahaz.domain.repository.register

import RegisterResponseDto
import com.task.yogahaz.data.dto.register.request.RegisterBody

interface RegisterRepository {
    suspend fun register( body: RegisterBody): RegisterResponseDto
}