package com.example.littlelemon.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.littlelemon.DishDetail
import com.example.littlelemon.Home
import com.example.littlelemon.data.model.Dish
import com.example.littlelemon.functions.extended.showMessage
import com.example.littlelemon.functions.helper.popBack
import com.example.littlelemon.ui.components.AppButton
import com.example.littlelemon.ui.components.LittleLemonTopBar


@Composable
fun DishDetailScreen(
    dish: Dish?,
    horizontalScreenPadding: Dp,
    context: Context,
    navController: NavHostController
) {
    Scaffold(
        topBar = { LittleLemonTopBar(screen = DishDetail, navController = navController) },
        bottomBar = {
            BottomBar(
                dish = dish,
                horizontalScreenPadding = horizontalScreenPadding,
                context = context,
                navController = navController
            )
        }
    ) { innerPadding ->
        dishFrame(
            dish = dish,
            innerPadding = innerPadding,
            horizontalScreenPadding = horizontalScreenPadding
        )
    }
}

@Composable
private fun dishFrame(
    dish: Dish?,
    innerPadding: PaddingValues,
    horizontalScreenPadding: Dp
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        dish?.let {
            AsyncImage(
                model = dish.image,
                contentDescription = "${dish.title} image",
                contentScale = ContentScale.FillWidth
            )

            Column(
                Modifier
                    .height(180.dp)
                    .padding(horizontal = horizontalScreenPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = dish.title, style = MaterialTheme.typography.titleMedium)
                Text(dish.description)
                Text(text = "$${dish.price}", style = MaterialTheme.typography.titleMedium)
            }

        }
    }
}


@Composable
private fun BottomBar(
    dish: Dish?,
    horizontalScreenPadding: Dp,
    context: Context,
    navController: NavHostController
) {
    val isPreviousScreenHome =
        navController.previousBackStackEntry?.destination?.route == Home.route

    AppButton(
        modifier = Modifier
            .padding(horizontal = horizontalScreenPadding)
            .navigationBarsPadding(),
        title = if (isPreviousScreenHome) "Add to cart" else "Remove item",
        onClick = {
            dish?.let {

                if (isPreviousScreenHome) {
                    basketItems.add(dish)
                } else basketItems.remove(dish)

                context.showMessage(
                    if (isPreviousScreenHome)
                        "${dish.title} has been added to your basket"
                    else
                        "${dish.title} has been removed from your basket"
                )
                popBack(navController = navController)

            }
        }
    )
}