package com.example.littlelemon.ui.screens


import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.littlelemon.Home
import com.example.littlelemon.R
import com.example.littlelemon.data.model.FieldNames
import com.example.littlelemon.data.model.TextFieldValues
import com.example.littlelemon.data.model.UserInputState
import com.example.littlelemon.functions.extended.showMessage
import com.example.littlelemon.ui.components.AppButton
import com.example.littlelemon.ui.components.AppTextField


@Composable
fun OnBoardingScreen(
    horizontalScreenPadding: Dp,
    context: Context,
    sharedPreferences: SharedPreferences,
    navController: NavHostController
) {
    val topSpaceBetweenItems = 50.0
    LazyColumn(
        Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        item { Spacer(Modifier.height(topSpaceBetweenItems.dp + 10.dp)) }
        item { OnBoardingHeader(space = topSpaceBetweenItems) }
        item {
            OnBoardingForm(
                context = context,
                sharedPreferences = sharedPreferences,
                space = topSpaceBetweenItems,
                horizontalSpace = horizontalScreenPadding,
                navController = navController,
            )
        }
        item { Spacer(Modifier.height(topSpaceBetweenItems.dp)) }
    }
}

@Composable
fun OnBoardingHeader(space: Double) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(.9f)
                .height(80.dp)
        )
        Box(
            Modifier
                .padding(top = space.dp)
                .fillMaxWidth()
                .height(150.dp)
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let’s get to know you",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun OnBoardingForm(
    context: Context,
    space: Double,
    horizontalSpace: Dp,
    navController: NavHostController,
    sharedPreferences: SharedPreferences
) {

    var userInput by remember { mutableStateOf(UserInputState()) }

    val textFieldValues = listOf(
        TextFieldValues().getFieldValue(FieldNames.FirstName, userInput),
        TextFieldValues().getFieldValue(FieldNames.LastName, userInput),
        TextFieldValues().getFieldValue(FieldNames.Email, userInput),
        TextFieldValues().getFieldValue(FieldNames.Password, userInput),
        TextFieldValues().getFieldValue(FieldNames.ConfirmPassword, userInput),
    )

    Column(
        Modifier
            .padding(top = space.dp)
            .padding(horizontal = horizontalSpace)
    ) {

        Text(
            text = "Personal information",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(Modifier.height(15.dp))

        textFieldValues.forEach { field ->
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
            title = "Register",
            onClick = {
                userInput = userInput.validData()

                if (userInput.thereIsNoError) {
                    navController.navigate(Home.route)
                    sharedPreferences.edit{
                        putBoolean("isUserOnboarding", false)
                        putString(FieldNames.FirstName.name, userInput.firstName.trim())
                        putString(FieldNames.LastName.name, userInput.lastName.trim())
                        putString(FieldNames.Email.name, userInput.email)
                        putString(FieldNames.Password.name, userInput.password)
                    }
                }

                context.showMessage(
                    if(userInput.thereIsNoError)
                        "Registration successful!"
                    else
                        "Registration unsuccessful. Please review the form."
                )
            }
        )
    }
}