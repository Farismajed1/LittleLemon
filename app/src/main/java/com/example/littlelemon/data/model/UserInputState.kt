package com.example.littlelemon.data.model

import android.util.Patterns

data class UserInputState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    var previousPassword: String = "",
    var isFirstNameValid: Boolean = true,
    var isLastNameValid: Boolean = true,
    var isEmailValid: Boolean = true,
    var isPasswordValid: Boolean = true,
    var isPreviousPasswordValid: Boolean = true,
    var isAllDataValid: Boolean = true,
    val nameErrorMessage: String = "Name is not Valid.",
    val emailErrorMessage: String = "Email is not Valid.",
    var passwordErrorMessage: String = "",
    val previousPasswordErrorMessage: String = "The previous password you entered is incorrect.",
) {
    fun validData(): UserInputState {

        val checkName = { name: String ->
            name.all { it.isLetter() || it.isWhitespace() } && name.length > 1 && !name.trim()
                .contains("  ")
        }

        val firstName = checkName(this.firstName)

        val lastName =
            if (this.lastName.isNotEmpty())
                checkName(this.lastName)
            else true

        val email = Patterns.EMAIL_ADDRESS.matcher(this.email).matches()

        val password = (this.password == this.confirmPassword && this.password.length >= 8)

        val passwordErrorMessage = when {
            this.password.length < 8 -> "Password must be at least 8 characters long."
            this.password != confirmPassword -> "Passwords do not match."
            else -> ""
        }

        return copy(
            isFirstNameValid = firstName,
            isLastNameValid = lastName,
            isEmailValid = email,
            isPasswordValid = password,
            passwordErrorMessage = passwordErrorMessage,
            isAllDataValid = firstName && lastName && email && password
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