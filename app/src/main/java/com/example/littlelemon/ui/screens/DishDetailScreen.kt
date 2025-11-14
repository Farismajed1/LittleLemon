package com.example.littlelemon.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.littlelemon.functions.helper.isNetworkAvailable
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
        DishFrame(
            dish = dish,
            innerPadding = innerPadding,
            horizontalScreenPadding = horizontalScreenPadding,
            context = context
        )
    }
}

@Composable
private fun DishFrame(
    dish: Dish?,
    innerPadding: PaddingValues,
    horizontalScreenPadding: Dp,
    context: Context
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        dish?.let {
            Box(
                modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f),
                contentAlignment = Alignment.Center
            ) {
                if(isNetworkAvailable(context = context)){
                    AsyncImage(
                        model = dish.image,
                        contentDescription = "${dish.title} image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Photo not found no internet connection.")
                }
            }


            Column(Modifier.padding(horizontal = horizontalScreenPadding)) {
                Text(text = dish.title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(15.dp))
                Text(dish.description)
                Spacer(Modifier.height(15.dp))
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