import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.domain.usecases.login.LoginUseCase
import com.task.yogahaz.fakerepository.FakeLoginRepository
import com.task.yogahaz.presentation.login.viewmodel.LoginViewModel
import com.task.yogahaz.util.MainDispatcherRule
import com.task.yogahaz.utils.validation.ValidationExceptions
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFailsWith


@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = MainDispatcherRule()
    private lateinit var loginRepository: FakeLoginRepository
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        loginRepository = FakeLoginRepository()
        loginViewModel = LoginViewModel(LoginUseCase(loginRepository))
    }
    @After
    fun tearDown() {
    }

    @Test
    fun `login with valid data returns state with success`() = runTest {
        // Given
        val validLoginBody = LoginBody(email = "test@example.com", password = "12345678ab")
        val fakeResponse = LoginResponseDto(success = true,
            message = "Login Success", responseCode = 200,data =null)
        loginRepository.setFakeLoginsResponse(fakeResponse)// adding fake data to the repository

        // When
        loginViewModel.login(validLoginBody)
        // Then
        val state = loginViewModel.state.first()
        assertEquals("Login Success", state.loginResponse?.message)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `login with invalid email throws validation exception`() = runTest {
        // Given
        val invalidLoginBody = LoginBody(email = "xample.com", password = "12345678ab")
        val fakeResponse = LoginResponseDto(success = false,
            message = "Login failed", responseCode = 200,data =null)
        loginRepository.setFakeLoginsResponse(fakeResponse)// adding fake data to the repository
        // When
        val exception = assertFailsWith<ValidationExceptions.EmailValidationException.InvalidEmailFormatException> {
            loginViewModel.login(invalidLoginBody)
        }

        // Then
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `login with empty password throws validation exception`() {
        // Given
        val invalidLoginBody = LoginBody(email = "test@example.com", password = "")
        val fakeResponse = LoginResponseDto(success = false,
            message = "Login failed", responseCode = 200,data =null)
        loginRepository.setFakeLoginsResponse(fakeResponse)// adding fake data to the repository
        // When
        val exception = assertFailsWith<ValidationExceptions.PasswordValidationException.EmptyPasswordException> {
            loginViewModel.login(invalidLoginBody)
        }

        // Then
        assertEquals("Password number cannot be empty", exception.message)

    }
}
