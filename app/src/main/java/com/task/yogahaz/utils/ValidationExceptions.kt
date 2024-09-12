package com.task.yogahaz.utils

sealed class ValidationExceptions(message: String) : Exception(message) {

    sealed class NameValidationException(message: String) : ValidationExceptions(message) {
        class EmptyNameException : NameValidationException("Name cannot be empty")
        class InvalidNameFormatException : NameValidationException("Name should be at least 14 characters")
    }
    sealed class EmailValidationException(message: String) : ValidationExceptions(message) {
        class EmptyEmailException : EmailValidationException("Email cannot be empty")
        class InvalidEmailFormatException : EmailValidationException("Invalid email format")
    }
    sealed class PhoneValidationException(message: String) : ValidationExceptions(message) {
        class EmptyPhoneException : PhoneValidationException("Phone number cannot be empty")
        class InvalidPhoneFormatException : PhoneValidationException("Invalid phone number ")
    }
    sealed class PasswordValidationException(message: String) : ValidationExceptions(message) {
        class EmptyPasswordException : PasswordValidationException("Password number cannot be empty")
        class ShortPasswordException : PasswordValidationException("Password must contain at least 8 characters")
        class InvalidPasswordException : PasswordValidationException("Password must be letters and numbers")

    }
    class ConfirmPasswordValidationException : ValidationExceptions("Passwords do not match")
}
