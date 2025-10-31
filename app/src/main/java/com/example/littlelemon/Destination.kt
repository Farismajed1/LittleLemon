package com.example.littlelemon

interface Destination {
    val route: String
}

object OnBoarding: Destination {
    override val route: String = "OnBoardingScreen"
}

object Home: Destination {
    override val route: String = "HomeScreen"
}

object DishDetail: Destination {
    override val route: String = "DishScreen"
    const val dishIdArg = "dishId"
    val routeWithArgument = "$route/{$dishIdArg}"
}

object Profile: Destination {
    override val route: String = "ProfileScreen"
}

object ChangePassword: Destination {
    override val route: String = "ChangePasswordScreen"
}

object Basket: Destination {
    override val route: String = "BasketScreen"
}