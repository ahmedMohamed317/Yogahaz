package com.task.yogahaz.presentation.register.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.task.yogahaz.R
import com.task.yogahaz.utils.base.BaseFragment
import com.task.yogahaz.databinding.FragmentRegisterBinding
import com.task.yogahaz.presentation.register.viewmodel.RegisterViewModel
import com.task.yogahaz.utils.constants.UserData
import com.task.yogahaz.utils.Utils.addValidationWatcher
import com.task.yogahaz.utils.Utils.doesPasswordContainLettersAndNumbers
import com.task.yogahaz.utils.Utils.isPasswordMatching
import com.task.yogahaz.utils.Utils.isPasswordNotShort
import com.task.yogahaz.utils.Utils.isValidEmail
import com.task.yogahaz.utils.Utils.isValidName
import com.task.yogahaz.utils.Utils.isValidPhone
import com.task.yogahaz.utils.validation.ValidationExceptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.signUpBtn.setOnClickListener {
            clearInputErrors()
            observeRegister()
            val name = binding.userNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            try {
                viewModel.register(name, email, phone, password, confirmPassword)
            } catch (e: ValidationExceptions) {
                handleValidationException(e)
            }
        }
        binding.loginLayout.setOnClickListener {
            navigateToLoginFragment()
        }
    }


    private fun handleValidationException(exception: ValidationExceptions) {
        when (exception) {
            is ValidationExceptions.NameValidationException-> {
                binding.userNameInputLayout.error = exception.message
            }
            is ValidationExceptions.EmailValidationException-> {
                binding.emailInputLayout.error = exception.message
            }

            is ValidationExceptions.PhoneValidationException -> {
                binding.phoneInputLayout.error = exception.message
            }
            is ValidationExceptions.PasswordValidationException -> {
                binding.passwordInputLayout.error = exception.message
            }
            is ValidationExceptions.ConfirmPasswordValidationException -> {
                binding.confirmPasswordInputLayout.error = exception.message
            }
        }
    }

    private fun clearInputErrors() {    // clear inputs after clicking on button
        binding.userNameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.phoneInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null
    }

    override fun onCreateInit() {
        setUpInputLayoutsWatchers()
    }


    private fun navigateToLoginFragment() {
        if (checkCurrentDestination(R.id.registerFragment)) {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)

        }
    }

    private fun navigateToIHomeFragment(userName:String? , userAddress :String) {
        if (checkCurrentDestination(R.id.registerFragment)) {
            val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment(userName,userAddress)
            findNavController().navigate(action)
        }
    }
    private fun setUpInputLayoutsWatchers() { // watch for the layout to validate
        setUpUserNameWatcher()
        setUpEmailWatcher()
        setUpPasswordWatcher()
        setUpConfirmPasswordWatcher()
        setUpPhoneWatcher()
    }

    private fun setUpUserNameWatcher() {
        binding.userNameInputLayout.addValidationWatcher { input ->
            when {
                input.isBlank() -> binding.userNameInputLayout.error = null
                !isValidName(input)  -> throw ValidationExceptions.NameValidationException.InvalidNameFormatException()
            }
        }
    }

    private fun setUpEmailWatcher() {
        binding.emailInputLayout.addValidationWatcher { input ->
            when {
                input.isBlank() -> binding.userNameInputLayout.error = null
                !isValidEmail(input) -> throw ValidationExceptions.EmailValidationException.InvalidEmailFormatException()
            }
        }
    }

    private fun setUpPasswordWatcher() {
        binding.passwordInputLayout.addValidationWatcher { input ->
            when {
                input.isBlank() -> binding.userNameInputLayout.error = null
                !isPasswordNotShort(input) -> throw ValidationExceptions.PasswordValidationException.ShortPasswordException()
                !doesPasswordContainLettersAndNumbers(input) -> throw ValidationExceptions.PasswordValidationException.InvalidPasswordException()
            }
        }
    }

    private fun setUpConfirmPasswordWatcher() {
        binding.confirmPasswordInputLayout.addValidationWatcher { input ->
            when {
                input.isBlank() -> binding.userNameInputLayout.error = null
                !isPasswordMatching(input,binding.passwordEditText.text.toString()) -> throw ValidationExceptions.ConfirmPasswordValidationException()
            }
        }
    }

    private fun setUpPhoneWatcher() {
        binding.phoneInputLayout.addValidationWatcher { input ->
            when {
                input.isBlank() -> binding.userNameInputLayout.error = null
                !isValidPhone(input) -> throw ValidationExceptions.PhoneValidationException.InvalidPhoneFormatException()
            }
        }
    }
    private fun observeRegister() {

        viewModel.state.onEach { state ->
            if (state.isLoading) {
                showProgressBar()
            } else {

                dismissProgressBar()
            }

            state.error.let {

                if (state.error.isNotBlank()) {
                    showToast(it)
                }
            }

            state.registerResponse?.let {
                UserData.TOKEN = it.data?.token?:""
                showToast(getString(R.string.registration_successful))

                val address = it.data?.addresses?.firstOrNull()?.address
                    ?: context?.getString(R.string.no_address_added)
                navigateToIHomeFragment(state.registerResponse.data?.name,
                    address ?:""
                )


            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}

