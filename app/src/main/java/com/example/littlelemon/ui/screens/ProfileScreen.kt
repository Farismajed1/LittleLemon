package com.example.littlelemon.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.littlelemon.ChangePassword
import com.example.littlelemon.OnBoarding
import com.example.littlelemon.Profile
import com.example.littlelemon.data.model.FieldNames
import com.example.littlelemon.data.model.TextFieldValues
import com.example.littlelemon.data.model.UserInputState
import com.example.littlelemon.functions.extended.showMessage
import com.example.littlelemon.functions.helper.getUserInfo
import com.example.littlelemon.ui.components.AppButton
import com.example.littlelemon.ui.components.AppTextField
import com.example.littlelemon.ui.components.LittleLemonTopBar


@Composable
fun ProfileScreen(
    horizontalScreenPadding: Dp,
    context: Context,
    sharedPreferences: SharedPreferences,
    navController: NavHostController
) {

    var userInput by remember {
        mutableStateOf(
            UserInputState(
                firstName = getUserInfo(
                    sharedPreferences = sharedPreferences,
                    field = FieldNames.FirstName
                ),
                lastName = getUserInfo(
                    sharedPreferences = sharedPreferences,
                    field = FieldNames.LastName
                ),
                email = getUserInfo(
                    sharedPreferences = sharedPreferences,
                    field = FieldNames.Email
                )
            )
        )
    }

    var logout by remember { mutableStateOf(false) }

    val textFieldValue = listOf(
        TextFieldValues().getFieldValue(FieldNames.FirstName, userInput),
        TextFieldValues().getFieldValue(FieldNames.LastName, userInput),
        TextFieldValues().getFieldValue(FieldNames.Email, userInput)
    )

    Scaffold(
        topBar = { LittleLemonTopBar(navController = navController, screen = Profile) }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = horizontalScreenPadding)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Personal information",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(35.dp))
            textFieldValue.forEach { field ->
                AppTextField(
                    title = field.title.name,
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
                onClick = { navController.navigate(ChangePassword.route) }
            )
            AppButton(
                title = "Save change",
                onClick = {

                    userInput = userInput.validData()

                    userInput = userInput.copy(
                        thereIsNoError = !userInput.isFirstNameError && !userInput.isLastNameError && !userInput.isEmailError
                    )

                    val isItSameInfo =
                        userInput.firstName.trim() == getUserInfo(
                            sharedPreferences = sharedPreferences,
                            field = FieldNames.FirstName
                        ).trim() &&
                                userInput.lastName.trim() == getUserInfo(
                            sharedPreferences = sharedPreferences,
                            field = FieldNames.LastName
                        ).trim() &&
                                userInput.email == getUserInfo(
                            sharedPreferences = sharedPreferences,
                            field = FieldNames.Email
                        )

                    if (userInput.thereIsNoError && !isItSameInfo) {
                        sharedPreferences.edit {
                            putString(FieldNames.FirstName.name, userInput.firstName.trim())
                            putString(FieldNames.LastName.name, userInput.lastName.trim())
                            putString(FieldNames.Email.name, userInput.email)
                        }
                    }

                    if (userInput.thereIsNoError)
                        context.showMessage(
                            if (!isItSameInfo) "Your changes have been saved successfully." else "No new information to update."
                        )
                },
            )
            AppButton(
                title = "Logout",
                onClick = { logout = true }
            )
        }

        val closeDialog = { logout = false }

        if (logout) {
            AlertDialog(
                onDismissRequest = { closeDialog() },
                title = { Text(text = "Logout") },
                text = {
                    Text("Are you sure you want to Logout")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            sharedPreferences.edit {
                                putBoolean("isUserOnboarding", true)
                                navController.navigate(OnBoarding.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }

                                context.showMessage("Logout successful")
                            }

                            closeDialog()
                        },
                    ) {
                        Text(
                            text = "Yes",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { closeDialog() }
                    ) {
                        Text(
                            text = "Cancel",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    }
}