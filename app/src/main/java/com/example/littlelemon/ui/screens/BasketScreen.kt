package com.example.littlelemon.ui.screens


import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.Basket
import com.example.littlelemon.DishDetail
import com.example.littlelemon.data.model.Dish
import com.example.littlelemon.functions.extended.showMessage
import com.example.littlelemon.functions.helper.popBack
import com.example.littlelemon.ui.components.AppButton
import com.example.littlelemon.ui.components.DishFrame
import com.example.littlelemon.ui.components.LittleLemonTopBar


val basketItems = mutableListOf<Dish>()

@Composable
fun BasketScreen(
    horizontalScreenPadding: Dp,
    context: Context,
    navController: NavHostController,
) {

    val totalPrice = remember { mutableIntStateOf(basketItems.sumOf { it.price.toInt() }) }

    Scaffold(
        topBar = { LittleLemonTopBar(screen = Basket, navController = navController) },
        bottomBar = {
            BottomBar(
                horizontalScreenPadding = horizontalScreenPadding,
                totalPrice = totalPrice,
                context = context,
                navController = navController
            )
        }
    ) { innerPadding ->
        if (basketItems.isNotEmpty()) {
            ShowBasket(
                innerPadding = innerPadding,
                horizontalScreenPadding = horizontalScreenPadding,
                context = context,
                navController = navController
            )
        } else {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Basket is empty.")
            }
        }
    }
}


@Composable
private fun ClearBasket(
    context: Context,
    navController: NavHostController
) {
    if (basketItems.isNotEmpty()) {
        Button(
            modifier = Modifier.padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                basketItems.clear()
                popBack(navController = navController)
                context.showMessage("Basket has been cleared.")
            }
        ) {
            Text("Clear basket")
        }
    }
}


@Composable
private fun ShowBasket(
    innerPadding: PaddingValues,
    horizontalScreenPadding: Dp,
    context: Context,
    navController: NavHostController
) {
    LazyColumn(
        Modifier
            .padding(innerPadding)
            .padding(horizontal = horizontalScreenPadding)
    ) {
        item {
            ClearBasket(
                context = context,
                navController = navController
            )
        }

        items(basketItems) { item ->
            HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)

            DishFrame(
                horizontalScreenPadding = horizontalScreenPadding,
                dish = item,
                onClick = { navController.navigate("${DishDetail.route}/${item.id}") }
            )
        }
    }
}


@Composable
private fun BottomBar(
    horizontalScreenPadding: Dp,
    totalPrice: MutableIntState,
    context: Context,
    navController: NavHostController
) {
    Column(
        Modifier
            .padding(horizontal = horizontalScreenPadding)
            .navigationBarsPadding()
            .padding(top = 3.dp)
    ) {
        Text(
            text = "Total price $${totalPrice.intValue}"
        )
        AppButton(
            title = "Payment",
            onClick = {

                if (basketItems.isNotEmpty()) {
                    basketItems.clear()
                    popBack(navController = navController)
                }

                context.showMessage(
                    if (basketItems.isNotEmpty())
                        "Payment successful — thank you for choosing Little Lemon!"
                    else
                        "Basket is empty"
                )
            }
        )
    }
}