package com.example.littlelemon.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.littlelemon.ChangePassword
import com.example.littlelemon.data.model.FieldNames
import com.example.littlelemon.data.model.TextFieldValues
import com.example.littlelemon.data.model.UserInputState
import com.example.littlelemon.functions.extended.showMessage
import com.example.littlelemon.functions.helper.getUserInfo
import com.example.littlelemon.functions.helper.popBack
import com.example.littlelemon.ui.components.AppButton
import com.example.littlelemon.ui.components.AppTextField
import com.example.littlelemon.ui.components.LittleLemonTopBar

@Composable
fun ChangePasswordScreen(
    horizontalScreenPadding: Dp,
    context: Context,
    sharedPreferences: SharedPreferences,
    navController: NavHostController
) {
    Scaffold(
        topBar = { LittleLemonTopBar(screen = ChangePassword, navController = navController) }
    ) { innerPadding ->
        ChangePasswordForm(
            innerPadding = innerPadding,
            horizontalScreenPadding = horizontalScreenPadding,
            sharedPreferences = sharedPreferences,
            context = context,
            navController = navController
        )
    }
}

@Composable
private fun ChangePasswordForm(
    innerPadding: PaddingValues,
    horizontalScreenPadding: Dp,
    sharedPreferences: SharedPreferences,
    context: Context,
    navController: NavHostController
){
    var userInput by remember { mutableStateOf(UserInputState()) }

    val textFieldValues = listOf(
        TextFieldValues(FieldNames.PreviousPassword).getFieldValue(userInput),
        TextFieldValues(FieldNames.Password).getFieldValue(userInput),
        TextFieldValues(FieldNames.ConfirmPassword).getFieldValue(userInput)
    )

    Column(
        Modifier
            .padding(innerPadding)
            .padding(horizontal = horizontalScreenPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Change Password", style = MaterialTheme.typography.titleMedium)

        textFieldValues.forEach { field ->
            AppTextField(
                title = if (field.title == FieldNames.FirstName) "Previous password" else field.title.name,
                value = field.value,
                onValueChange = { input ->
                    userInput = userInput.updateData(field = field.title, value = input)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = field.keyboardType,
                    imeAction = field.imeAction
                ),
                visualTransformation = field.visualTransformation,
                isError = field.isError,
                errorMessage = field.errorMessage
            )
        }

        AppButton(
            title = "Change password",
            onClick = {

                userInput = userInput.validData()

                userInput = userInput.copy(
                    isPreviousPasswordValid = userInput.previousPassword == getUserInfo(
                        sharedPreferences,
                        FieldNames.Password
                    ),
                    isAllDataValid = userInput.isPasswordValid && userInput.isPreviousPasswordValid
                )

                if (userInput.isAllDataValid) {

                    sharedPreferences.edit {
                        putString(FieldNames.Password.name, userInput.password)
                    }

                    popBack(navController = navController)
                }

                context.showMessage(
                    if (userInput.isAllDataValid)
                        "Password has been changed successfully."
                    else
                        "An error occurred. Please try again."
                )
            }
        )
    }
}