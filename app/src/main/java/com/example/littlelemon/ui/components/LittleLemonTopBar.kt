package com.example.littlelemon.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.Basket
import com.example.littlelemon.Destination
import com.example.littlelemon.Home
import com.example.littlelemon.Profile
import com.example.littlelemon.R
import com.example.littlelemon.functions.helper.popBack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LittleLemonTopBar(screen: Destination, navController: NavHostController) {
    CenterAlignedTopAppBar(

        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp),

        title = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.logo),
                contentDescription = "Little Lemon Logo"
            )
        },

        actions = {
            if (screen == Home) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = {
                            navController.navigate(Profile.route)
                        }
                        ),
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile image"
                )
            }
        },

        navigationIcon = {
            if (screen == Home) {
                IconButton(onClick = { navController.navigate(Basket.route) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_basket),
                        contentDescription = "Basket button"
                    )
                }
            } else {
                IconButton(
                    onClick = { popBack(navController = navController) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_arrow),
                        contentDescription = "Back button"
                    )
                }
            }
        }
    )
}