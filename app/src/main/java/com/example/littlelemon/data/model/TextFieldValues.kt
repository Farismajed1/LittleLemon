package com.example.littlelemon.data.model


import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class TextFieldValues(
    var title: FieldNames = FieldNames.FirstName,
    var value: String = "",
    var isError: Boolean = false,
    var keyboardType: KeyboardType = KeyboardType.Text,
    var imeAction: ImeAction = ImeAction.Next,
    var visualTransformation: VisualTransformation = VisualTransformation.None,
    var errorMessage: String = "",
) {

    fun getFieldValue(fieldName: FieldNames, userInput: UserInputState): TextFieldValues {

        return when(fieldName) {

            FieldNames.FirstName ->
                TextFieldValues(
                    title = FieldNames.FirstName,
                    value = userInput.firstName,
                    isError = userInput.isFirstNameError,
                    errorMessage = userInput.nameErrorMessage
                )

            FieldNames.LastName ->
                TextFieldValues(
                    title = FieldNames.LastName,
                    value = userInput.lastName,
                    isError = userInput.isLastNameError,
                    errorMessage = userInput.nameErrorMessage
                )

            FieldNames.Email ->
                TextFieldValues(
                    title = FieldNames.Email,
                    value = userInput.email,
                    keyboardType = KeyboardType.Email,
                    isError = userInput.isEmailError,
                    errorMessage = userInput.emailErrorMessage
                )

            FieldNames.Password ->
                TextFieldValues(
                    title = FieldNames.Password,
                    value = userInput.password,
                    keyboardType = KeyboardType.Password,
                    isError = userInput.isPasswordError,
                    visualTransformation = PasswordVisualTransformation(),
                    imeAction = ImeAction.Done,
                    errorMessage = userInput.passwordErrorMessage
                )

            FieldNames.ConfirmPassword ->
                TextFieldValues(
                    title = FieldNames.ConfirmPassword,
                    value = userInput.confirmPassword,
                    keyboardType = KeyboardType.Password,
                    isError = userInput.isPasswordError,
                    visualTransformation = PasswordVisualTransformation(),
                    imeAction = ImeAction.Done,
                )

            FieldNames.PreviousPassword ->
                TextFieldValues(
                    title = FieldNames.PreviousPassword,
                    value = userInput.previousPassword,
                    isError = userInput.isPreviousPasswordError,
                    errorMessage = userInput.previousPasswordErrorMessage
                )
        }
    }
}
