package com.task.yogahaz.presentation.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.domain.usecases.login.LoginUseCase
import com.task.yogahaz.presentation.login.state.LoginState
import com.task.yogahaz.utils.constants.CONSTANTS
import com.task.yogahaz.utils.network.Result
import com.task.yogahaz.utils.Utils
import com.task.yogahaz.utils.validation.ValidationExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCase: LoginUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(body: LoginBody) {
        validateInputs(body)
        try {
            performLogin(body)
        } catch (e: Exception) {
            _state.value = LoginState(error = e.message ?: "An unexpected error occurred")
        }
    }

    private fun performLogin(loginBody: LoginBody) {
        getLoginUseCase(loginBody).onEach { result ->

            when (result) {
                is Result.Success -> {
                    _state.value = LoginState(loginResponse = result.data, isLoading = false)
                }
                is Result.Error -> {
                    _state.value = LoginState(
                        error = result.message ?: "An unexpected error occurred", isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateInputs(body: LoginBody) {
            when {
                body.email.isBlank() -> throw ValidationExceptions.EmailValidationException.EmptyEmailException()
                !Utils.isValidEmail(body.email)
                     -> throw ValidationExceptions.EmailValidationException.InvalidEmailFormatException()

                body.password.isBlank() -> throw ValidationExceptions.PasswordValidationException.EmptyPasswordException()
                body.password.length < CONSTANTS.MIN_PASSWORD_LENGTH -> throw ValidationExceptions.PasswordValidationException.ShortPasswordException()

            }
        }



}
