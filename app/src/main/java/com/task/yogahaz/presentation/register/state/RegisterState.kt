package com.task.yogahaz.presentation.register.state

import com.task.yogahaz.domain.models.auth.RegisterResponse


data class RegisterState(   // initial state of register
    val isLoading: Boolean = false,
    val registerResponse: RegisterResponse? = null,
    val error: String = ""
)