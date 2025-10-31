package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.data.model.Dish
import com.example.littlelemon.ui.screens.BasketScreen
import com.example.littlelemon.ui.screens.ChangePasswordScreen
import com.example.littlelemon.ui.screens.DishDetailScreen
import com.example.littlelemon.ui.screens.HomeScreen
import com.example.littlelemon.ui.screens.OnBoardingScreen
import com.example.littlelemon.ui.screens.ProfileScreen

@Composable
fun Navigation(
    horizontalScreenPadding: Dp,
    context: Context,
    dishList: List<Dish>
    ){

    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val isUserOnboarding = sharedPreferences.getBoolean("isUserOnboarding", true)

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if(isUserOnboarding) OnBoarding.route else Home.route
    ){

        composable(route = OnBoarding.route) {
            OnBoardingScreen(
                horizontalScreenPadding = horizontalScreenPadding,
                context = context,
                sharedPreferences = sharedPreferences,
                navController = navController
            )
        }

        composable(Home.route) {
            HomeScreen(
                horizontalScreenPadding = horizontalScreenPadding,
                dishList = dishList,
                navController = navController
            )
        }

        composable(
            route = DishDetail.routeWithArgument,
            arguments = listOf(navArgument(DishDetail.dishIdArg) { type = NavType.IntType }) // Specify argument type
        ) { navBackStackEntry ->
            val dishId = navBackStackEntry.arguments?.getInt(DishDetail.dishIdArg)
            val dish = dishList.find { it.id == dishId }
            DishDetailScreen(
                horizontalScreenPadding = horizontalScreenPadding,
                dish = dish,
                context = context,
                navController = navController
            )
        }

        composable(Profile.route){
            ProfileScreen(
                horizontalScreenPadding = horizontalScreenPadding,
                context = context,
                sharedPreferences = sharedPreferences,
                navController = navController
            )
        }

        composable(ChangePassword.route){
            ChangePasswordScreen(
                horizontalScreenPadding = horizontalScreenPadding,
                context = context,
                sharedPreferences = sharedPreferences,
                navController = navController
            )
        }

        composable(Basket.route) {
            BasketScreen(
                context = context,
                horizontalScreenPadding = horizontalScreenPadding,
                navController = navController
            )
        }
    }
}