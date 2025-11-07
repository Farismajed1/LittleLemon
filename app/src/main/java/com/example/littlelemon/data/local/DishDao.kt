package com.example.littlelemon.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.littlelemon.data.model.Dish

@Dao
interface DishDao {
    @Query("SELECT * FROM Dish")
    fun getAll(): LiveData<List<Dish>>

    @Query("SELECT (SELECT COUNT(*) FROM Dish) == 0")
    fun isEmpty(): Boolean

    @Insert
    fun insertAll(vararg dishes: Dish)

}