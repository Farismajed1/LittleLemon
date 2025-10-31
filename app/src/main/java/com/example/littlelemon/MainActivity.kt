package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.model.CallDishes
import com.example.littlelemon.data.model.CallMenu
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android){
        install(ContentNegotiation) {
            json(contentType = ContentType(contentType = "text", contentSubtype = "plain"))
        }
    }

    private val database by lazy { AppDatabase.getInstance(applicationContext) }

    private val dishDao by lazy { database.dishDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val horizontalScreenPadding = 18.dp

        setContent {
            LittleLemonTheme {

                val menuItems = dishDao.getAll().observeAsState(emptyList())


                Surface {
                    Navigation(
                        horizontalScreenPadding = horizontalScreenPadding,
                        context = applicationContext,
                        dishList = menuItems.value
                    )
                }
            }
        }//setContent


        lifecycleScope.launch(Dispatchers.IO){
            if(dishDao.isEmpty()){
                val menuItemNetwork = fetchData()
                saveMenuToDatabase(menuItemNetwork)
            }
        }

    }//onCreate

    private suspend fun fetchData(): List<CallDishes> {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<CallMenu>().menu
    }

    private fun saveMenuToDatabase(menuItem: List<CallDishes>) {
        val menuItemToRoom = menuItem.map{ it.toRoomDish() }
        dishDao.insertAll(*menuItemToRoom.toTypedArray())
    }

}//MainActivity
