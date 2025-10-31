package com.example.littlelemon.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DishDao {
    @Query("SELECT * FROM Dish")
    fun getAll(): LiveData<List<Dish>>

    @Query("SELECT (SELECT COUNT(*) FROM Dish) == 0")
    fun isEmpty(): Boolean

    @Insert
    fun insertAll(vararg dishes: Dish)

}