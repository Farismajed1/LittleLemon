package com.example.littlelemon.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CallMenu(
    @SerialName("menu")
    val menu: List<CallDishes>
)

@Serializable
data class CallDishes(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: String,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
){
    fun toRoomDish() = Dish(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}
