package com.example.littlelemon.functions.helper

import android.content.SharedPreferences
import com.example.littlelemon.data.model.FieldNames


fun getUserInfo(sharedPreferences: SharedPreferences, field: FieldNames): String {
    return sharedPreferences.getString(field.name, "") ?: ""
}