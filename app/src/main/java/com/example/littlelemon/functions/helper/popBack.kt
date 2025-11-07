package com.example.littlelemon.functions.helper

import androidx.navigation.NavHostController

fun popBack(navController: NavHostController) = navController.previousBackStackEntry?.let{
    navController.popBackStack()
}