package com.example.littlelemon.data.model


import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class TextFieldValues(
    var title: FieldNames,
    var value: String = "",
    var isError: Boolean = false,
    var keyboardType: KeyboardType = KeyboardType.Text,
    var imeAction: ImeAction = ImeAction.Next,
    var visualTransformation: VisualTransformation = VisualTransformation.None,
    var errorMessage: String = "",
) {

    fun getFieldValue(userInput: UserInputState): TextFieldValues {

        return when(this.title) {

            FieldNames.FirstName ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.firstName,
                    isError = !userInput.isFirstNameValid,
                    errorMessage = userInput.nameErrorMessage
                )

            FieldNames.LastName ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.lastName,
                    isError = !userInput.isLastNameValid,
                    errorMessage = userInput.nameErrorMessage
                )

            FieldNames.Email ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.email,
                    keyboardType = KeyboardType.Email,
                    isError = !userInput.isEmailValid,
                    errorMessage = userInput.emailErrorMessage
                )

            FieldNames.Password ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.password,
                    keyboardType = KeyboardType.Password,
                    isError = !userInput.isPasswordValid,
                    visualTransformation = PasswordVisualTransformation(),
                    imeAction = ImeAction.Done,
                    errorMessage = userInput.passwordErrorMessage
                )

            FieldNames.ConfirmPassword ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.confirmPassword,
                    keyboardType = KeyboardType.Password,
                    isError = !userInput.isPasswordValid,
                    visualTransformation = PasswordVisualTransformation(),
                    imeAction = ImeAction.Done,
                )

            FieldNames.PreviousPassword ->
                TextFieldValues(
                    title = this.title,
                    value = userInput.previousPassword,
                    isError = !userInput.isPreviousPasswordValid,
                    errorMessage = userInput.previousPasswordErrorMessage
                )
        }
    }
}
