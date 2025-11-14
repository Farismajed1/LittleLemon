package com.example.littlelemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.data.local.DishDao
import com.example.littlelemon.data.model.CallDishes
import com.example.littlelemon.data.model.CallMenu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(private val dishDao: DishDao, private val isNetworkAvailable: Boolean): ViewModel() {

    private val httpClient = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }
    }


    val menu = dishDao.getAll()

    init {
        viewModelScope.launch(Dispatchers.IO){
            if(isNetworkAvailable){
                if(!dishDao.isEmpty()){
                    dishDao.deleteAll()
                }
                val getMenu = fetchData()
                saveDataInRoom(getMenu)
            }
        }
    }

    //Get data from API
    private suspend fun fetchData(): List<CallDishes> {
        return httpClient
            .get("https://raw.githubusercontent.com/Farismajed1/API/refs/heads/main/LittleLemonAPI.js")
            .body<CallMenu>().menu
    }

    //Put the data from API inside room
    private fun saveDataInRoom(menu: List<CallDishes>){
        val menuToRoom = menu.map{ it.toRoomDish() }
        dishDao.insertAll( * menuToRoom.toTypedArray() )
    }
}