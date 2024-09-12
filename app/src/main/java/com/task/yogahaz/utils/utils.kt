package com.task.yogahaz.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputLayout
import com.task.yogahaz.utils.constants.CONSTANTS
import com.task.yogahaz.utils.validation.ValidationExceptions


object Utils {


    // loading the image with glide and applying center crop and rounded corners also managing placeholders
    fun loadImage(context: Context
                  , path : String
                  , imageView: ImageView
                  ,placeholder: Int
                  ,cornerRadiusValue: Int
    ) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(cornerRadiusValue))

        Glide.with(context)
            .load(path)
            .apply(requestOptions)
            .placeholder(placeholder)
            .into(imageView)

    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidName(name: String): Boolean {
        return name.length >= CONSTANTS.MIN_NAME_LENGTH
    }

    fun isValidPhone(phone: String): Boolean {
        val phoneRegex = Regex("^01[0-2,5][0-9]{8}$")
        return phone.matches(phoneRegex)
    }

    fun isPasswordNotShort(password: String): Boolean {
        return password.length >= CONSTANTS.MIN_PASSWORD_LENGTH
    }

    fun doesPasswordContainLettersAndNumbers(password: String): Boolean {
        return password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    inline fun TextInputLayout.clearErrorOnTextChanged(
        crossinline beforeTextChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
        crossinline onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
        crossinline afterTextChanged: (s: Editable?) -> Unit = {}
    ) {
        this.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s, start, before, count)
                this@clearErrorOnTextChanged.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChanged(s)
            }
        })
    }

    fun TextInputLayout.addValidationWatcher(
        validate: (String) -> Unit
    ) {
        this.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    validate(s.toString())
                    this@addValidationWatcher.error = null
                } catch (e: ValidationExceptions) {
                    this@addValidationWatcher.error = e.message
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
