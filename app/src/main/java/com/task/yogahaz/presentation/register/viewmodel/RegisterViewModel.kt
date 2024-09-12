package com.task.yogahaz.presentation.register.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.yogahaz.data.dto.register.request.RegisterBody
import com.task.yogahaz.domain.usecases.register.RegisterUseCase
import com.task.yogahaz.presentation.register.state.RegisterState
import com.task.yogahaz.utils.CONSTANTS
import com.task.yogahaz.utils.Result
import com.task.yogahaz.utils.ValidationExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getRegisterUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {

        validateInputs(name, email, phone, password, confirmPassword)
        val registerBody =
            RegisterBody(email = email, password = password, name = name, phone = phone)
        try {
            performRegister(registerBody)

        } catch (e: Exception) {
            _state.value = RegisterState(error = e.message ?: "An unexpected error occurred")
        }
    }

    private fun performRegister(registerBody: RegisterBody) {
        getRegisterUseCase(registerBody).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = RegisterState(registerResponse = result.data, isLoading = false)
                }

                is Result.Error -> {
                    _state.value = RegisterState(
                        error = result.message ?: "An unexpected error occurred", isLoading = false
                    )
                }

                is Result.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateInputs(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            name.isBlank() -> throw ValidationExceptions.NameValidationException.EmptyNameException()
            name.length < CONSTANTS.MIN_NAME_LENGTH -> throw ValidationExceptions.NameValidationException.InvalidNameFormatException()

            email.isBlank() -> throw ValidationExceptions.EmailValidationException.EmptyEmailException()
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> throw ValidationExceptions.EmailValidationException.InvalidEmailFormatException()

            phone.isBlank() -> throw ValidationExceptions.PhoneValidationException.EmptyPhoneException()
            !phone.matches(Regex("^01[0-2,5][0-9]{8}$")) -> throw ValidationExceptions.PhoneValidationException.InvalidPhoneFormatException()

            password.isBlank() -> throw ValidationExceptions.PasswordValidationException.EmptyPasswordException()
            password.length < CONSTANTS.MIN_PASSWORD_LENGTH -> throw ValidationExceptions.PasswordValidationException.ShortPasswordException()
            !password.any { it.isDigit() } || !password.any { it.isLetter() } -> throw ValidationExceptions.PasswordValidationException.InvalidPasswordException()

            password != confirmPassword -> throw ValidationExceptions.ConfirmPasswordValidationException()
        }
    }
}
