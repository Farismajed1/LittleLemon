package com.example.littlelemon.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Dish(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)
