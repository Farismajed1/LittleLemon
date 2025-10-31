package com.example.littlelemon.data.model

import android.util.Patterns

data class UserInputState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    var previousPassword: String = "",
    var isFirstNameError: Boolean = false,
    var isLastNameError: Boolean = false,
    var isEmailError: Boolean = false,
    var isPasswordError: Boolean = false,
    var isPreviousPasswordError: Boolean = false,
    var thereIsNoError: Boolean = true,
    val nameErrorMessage: String = "Name is not Valid.",
    val emailErrorMessage: String = "Email is not Valid.",
    var passwordErrorMessage: String = "",
    val previousPasswordErrorMessage: String = "The previous password you entered is incorrect.",
) {
    fun validData(): UserInputState {

        isFirstNameError =
            firstName.all { it.isLetter() || it.isWhitespace() } && firstName.length > 1 && !firstName.trim().contains(
                "  "
            )

        isLastNameError =
            if (lastName.isNotEmpty()) lastName.all { it.isLetter() || it.isWhitespace() } && lastName.length > 1 && !lastName.contains(
                "  "
            ) else true

        isEmailError = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        isPasswordError = (password == confirmPassword && password.length >= 8)

        passwordErrorMessage = when {
            password.length < 8 -> "Password must be at least 8 characters long."
            password != confirmPassword -> "Passwords do not match."
            else -> ""
        }

        return copy(
            isFirstNameError = !isFirstNameError,
            isLastNameError = !isLastNameError,
            isEmailError = !isEmailError,
            isPasswordError = !isPasswordError,
            passwordErrorMessage = passwordErrorMessage,
            thereIsNoError = isFirstNameError && isEmailError && isPasswordError && isLastNameError
        )
    }

    fun updateData(field: FieldNames, value: String): UserInputState {
        return when (field) {
            FieldNames.FirstName -> copy(firstName = value)
            FieldNames.LastName -> copy(lastName = value)
            FieldNames.Email -> copy(email = value)
            FieldNames.Password -> copy(password = value)
            FieldNames.ConfirmPassword -> copy(confirmPassword = value)
            FieldNames.PreviousPassword -> copy(previousPassword = value)
        }
    }

}