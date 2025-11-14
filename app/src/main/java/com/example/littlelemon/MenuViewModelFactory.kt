package com.example.littlelemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.littlelemon.data.local.DishDao

class MenuViewModelFactory(private val dishDao: DishDao, private val isNetworkAvailable: Boolean): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MenuViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(dishDao, isNetworkAvailable) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}