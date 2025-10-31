package com.example.littlelemon.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = Modifier
            .padding(vertical = 15.dp)
            .fillMaxWidth()
            .then(modifier),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(color = MaterialTheme.colorScheme.onPrimary, width = 1.5.dp),
        onClick = onClick
    ) {
        Text(text = title)
    }
}