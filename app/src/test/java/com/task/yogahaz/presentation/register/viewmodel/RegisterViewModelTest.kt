package com.task.yogahaz.presentation.register.viewmodel

import RegisterResponseDto
import com.task.yogahaz.domain.usecases.register.RegisterUseCase
import com.task.yogahaz.fakerepository.FakeRegisterRepository
import com.task.yogahaz.util.MainDispatcherRule
import com.task.yogahaz.utils.validation.ValidationExceptions
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFailsWith


@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    var instantExecutorRule = MainDispatcherRule()
    private lateinit var registerRepository: FakeRegisterRepository
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        registerRepository = FakeRegisterRepository()
        registerViewModel = RegisterViewModel(RegisterUseCase(registerRepository))
    }


    @Test
    fun `register with valid data returns state with success`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        registerViewModel.register(name ="Ahmed Mohamed Morad", email = "test@example.com",
            password = "12345678ab", phone = "01234567899", confirmPassword = "12345678ab")
        // Then
        val state = registerViewModel.state.first()
        assertEquals("Register Success", state.registerResponse?.message)
        assertEquals(false, state.isLoading)
    }


    @Test
    fun `register with invalid email throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions.EmailValidationException.InvalidEmailFormatException> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "example.com",  // use invalid email
                password = "12345678ab", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Invalid email format", exception.message)
    }


    @Test
    fun `register with empty email throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions.EmailValidationException.EmptyEmailException> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "",  // use invalid email
                password = "12345678ab", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Email cannot be empty", exception.message)
    }


    @Test
    fun `register with short name throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions.NameValidationException.InvalidNameFormatException> {
            registerViewModel.register(name ="Ahmed Mohamed", email = "test@example.com",  // use short name
                password = "12345678ab", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Name should be at least 14 characters", exception.message)
    }



    @Test
    fun `register with empty name throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions.NameValidationException.EmptyNameException> {
            registerViewModel.register(name ="", email = "test@example.com",  // use short name
                password = "12345678ab", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Name cannot be empty", exception.message)
    }

    @Test
    fun `register with short password throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "test@example.com",  // use short password
                password = "128ab", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Password must contain at least 8 characters", exception.message)
    }

    @Test
    fun `register with only numbers password throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "test@example.com",  // use only numbers password
                password = "12812414124", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Password must be letters and numbers", exception.message)
    }

    @Test
    fun `register with empty password throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "test@example.com",  // use empty password
                password = "", phone = "01234567899", confirmPassword = "12345678ab")
        }

        // Then
        assertEquals("Password number cannot be empty", exception.message)
    }

    @Test
    fun `register with confirm passwords not matching throws validation exception`() = runTest {
        // Given
        val fakeResponse = RegisterResponseDto(success = true,
            message = "Register Success", responseCode = 200,data =null)
        registerRepository.setFakeRegisterResponse(fakeResponse)// adding fake data to the repository

        // When
        val exception = assertFailsWith<ValidationExceptions> {
            registerViewModel.register(name ="Ahmed Mohamed Morad", email = "test@example.com",  //  passwords don't match
                password = "12345678ab", phone = "01234567899", confirmPassword = "12345678a")
        }

        // Then
        assertEquals("Passwords do not match", exception.message)
    }
}
