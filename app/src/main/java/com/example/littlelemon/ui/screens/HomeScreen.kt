package com.example.littlelemon.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.DishDetail
import com.example.littlelemon.Home
import com.example.littlelemon.R
import com.example.littlelemon.data.model.CategoriesNames
import com.example.littlelemon.data.model.Dish
import com.example.littlelemon.functions.helper.isNetworkAvailable
import com.example.littlelemon.ui.components.DishFrame
import com.example.littlelemon.ui.components.LittleLemonTopBar


@Composable
fun HomeScreen(
    horizontalScreenPadding: Dp,
    dishList: List<Dish>,
    context: Context,
    navController: NavHostController
) {

    var searchPhrase by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf(CategoriesNames.All) }


    val filteredDishes = dishList.filter { dish ->
        (selectedCategory == CategoriesNames.All || dish.category.contains(selectedCategory.name, ignoreCase = true)) &&
                (searchPhrase.isBlank() || dish.title.contains(searchPhrase, ignoreCase = true))
    }


    Scaffold(

        topBar = { LittleLemonTopBar(screen = Home, navController = navController) }

    ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .imePadding()
        ) {

            item {
                UpperPanel(
                    horizontalScreenPadding = horizontalScreenPadding,
                    searchPhrase = searchPhrase,
                    onSearchPhraseChange = { searchPhrase = it }
                )
            }

            item {
                Categories(
                    categories = CategoriesNames.entries,
                    selectedCategory = selectedCategory,
                    onCategoryClick = { selectedCategory = it }
                )
            }

            item {
                if(!isNetworkAvailable(context = context)){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.error)
                            .padding(all = 4.dp),
                        text = if(dishList.isEmpty()) "No dishes found. Please check your internet connection." else
                            "Your offline. Some data may be missing.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onError
                    )
                }
            }


            items(filteredDishes) { dish ->

                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary)

                DishFrame(
                    horizontalScreenPadding = horizontalScreenPadding,
                    dish = dish,
                    onClick = {
                        navController.navigate("${DishDetail.route}/${dish.id}")
                    }
                )
            }
        }
    }
}


@Composable
private fun UpperPanel(
    horizontalScreenPadding: Dp,
    searchPhrase: String,
    onSearchPhraseChange: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontalScreenPadding)
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Row(Modifier.height(150.dp)) {
                Column(Modifier.fillMaxWidth(.6f)) {
                    Text(
                        text = stringResource(R.string.city),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = stringResource(R.string.about_us),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.large),
                    painter = painterResource(R.drawable.hero_image),
                    contentDescription = "Hero image",
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(Modifier.height(15.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchPhrase,
                onValueChange = onSearchPhraseChange,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = ""
                    )
                },
                placeholder = { Text("Enter search phrase") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                )
            )

        }
    }
}



@Composable
private fun Categories(
    categories: List<CategoriesNames>,
    selectedCategory: CategoriesNames,
    onCategoryClick: (CategoriesNames) -> Unit
) {
    LazyRow(
        Modifier
            .padding(start = 8.dp)
            .padding(vertical = 8.dp)
    ) {
        items(categories) { category ->
            CategoryButton(
                buttonName = category.name,
                isSelected = category == selectedCategory,
                onClick = { onCategoryClick(category) }
            )
            Spacer(Modifier.width(8.dp))
        }
    }
}


@Composable
private fun CategoryButton(
    buttonName: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Text(
            text = buttonName,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}