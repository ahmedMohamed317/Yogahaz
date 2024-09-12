package com.task.yogahaz.presentation.login.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.task.yogahaz.R
import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.databinding.FragmentLoginBinding
import com.task.yogahaz.presentation.login.viewmodel.LoginViewModel
import com.task.yogahaz.utils.constants.UserData
import com.task.yogahaz.utils.Utils.clearErrorOnTextChanged
import com.task.yogahaz.utils.validation.ValidationExceptions
import com.task.yogahaz.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.loginBtn.setOnClickListener {
            clearInputErrors()
            observeLogin()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val loginBody = LoginBody(email, password)
            try {
                viewModel.login(loginBody)
            }
            catch (e: ValidationExceptions){
                handleValidationException(e)
            }
        }

        binding.signupLayout.setOnClickListener {
            navigateToRegisterFragment()
        }

    }



    override fun onCreateInit() {
        setUpClearWatchers()

    }



    private fun handleValidationException(exception: ValidationExceptions) {
        when (exception) {
            is ValidationExceptions.EmailValidationException-> {
                binding.emailInputLayout.error = exception.message
            }
            is ValidationExceptions.PasswordValidationException -> {
                binding.passwordInputLayout.error = exception.message
            }
            else ->  showToast(exception.message)

        }
    }

    private fun clearInputErrors() {
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
    }

    private fun navigateToRegisterFragment() {
        if (checkCurrentDestination(R.id.loginFragment)) {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }
    }

    private fun navigateToIHomeFragment(userName:String? , userAddress :String) {
        if (checkCurrentDestination(R.id.loginFragment)) {
            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName,userAddress)
            findNavController().navigate(action)

        }
    }
    private fun setUpClearWatchers(){ // clearing input layout if empty
        binding.emailInputLayout.clearErrorOnTextChanged()
        binding.passwordInputLayout.clearErrorOnTextChanged()
    }

    private fun observeLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.isLoading) {
                    showProgressBar()
                }
                else {
                    dismissProgressBar()
                }

                state.loginResponse?.let {
                    UserData.TOKEN = it.data?.token?:""
                    showToast(state.loginResponse.message)
                    val address = it.data?.addresses?.firstOrNull()?.address
                        ?: context?.getString(R.string.no_address_added)
                    navigateToIHomeFragment(state.loginResponse.data?.name,
                        address?:""
                    )
                }

                state.error.let {
                    if (state.error.isNotBlank()) {
                        showToast(it)
                    }
                }
            }
        }
    }
}
