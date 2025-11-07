package com.example.littlelemon.functions.helper

import android.content.SharedPreferences
import com.example.littlelemon.data.model.FieldNames
import com.example.littlelemon.data.model.UserInputState


fun checkMatch(
    userInput: String,
    sharedPreferences: SharedPreferences,
    field: FieldNames
): Boolean {
    return userInput.trim().equals(
        getUserInfo(
            sharedPreferences = sharedPreferences,
            field = field
        ),
        ignoreCase = true
    )
}