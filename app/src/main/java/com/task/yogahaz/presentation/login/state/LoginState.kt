package com.task.yogahaz.presentation.login.state

import com.task.yogahaz.domain.models.auth.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String = ""
)