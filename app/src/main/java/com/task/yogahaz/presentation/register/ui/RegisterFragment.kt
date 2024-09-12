package com.task.yogahaz.presentation.register.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.task.yogahaz.R
import com.task.yogahaz.base.BaseFragment
import com.task.yogahaz.databinding.FragmentRegisterBinding
import com.task.yogahaz.presentation.register.viewmodel.RegisterViewModel
import com.task.yogahaz.utils.UserData
import com.task.yogahaz.utils.ValidationExceptions
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

    override fun initViewModel() {
        viewModel.state.onEach { state ->
            if (state.isLoading) {
                showProgressBar()
            } else {
                dismissProgressBar()
            }

            state.error.let {
                if (state.error.isNotBlank()) {
                    showSnackBar(it)
                }
            }

            state.registerResponse?.let {
                UserData.TOKEN = it.data?.token?:""
                showSnackBar(getString(R.string.registration_successful))
                navigateToIHomeFragment(state.registerResponse.data?.name,
                    state.registerResponse.data?.addresses?.get(0)?.address ?: context?.getString(R.string.no_address_added )?:""
                )
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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

    private fun clearInputErrors() {
        binding.userNameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.phoneInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null
    }

    override fun onCreateInit() {
    }

    override fun initSetAdapter() {
    }

    override fun initToolBar() {
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
}
