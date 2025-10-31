package com.example.littlelemon.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.littlelemon.data.model.Dish


@Composable
fun DishFrame(
    horizontalScreenPadding: Dp,
    dish: Dish,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalScreenPadding, vertical = 8.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .fillMaxWidth(.7f)
                .height(95.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = dish.title, style = MaterialTheme.typography.titleSmall)
            Text(dish.description.take(65) + "...")
            Text("$${dish.price}")
        }
        AsyncImage(
            modifier = Modifier.size(95.dp),
            model = dish.image,
            contentDescription = "${dish.title} image",
            contentScale = ContentScale.Fit,
        )
    }
}