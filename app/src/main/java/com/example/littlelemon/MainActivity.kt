package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.functions.helper.isNetworkAvailable
import com.example.littlelemon.ui.theme.LittleLemonTheme


class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getInstance(applicationContext) }

    private val menuView by viewModels<MenuViewModel> {
        MenuViewModelFactory(database.dishDao(), isNetworkAvailable = isNetworkAvailable(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val horizontalScreenPadding = 18.dp

        setContent {
            LittleLemonTheme {

                val menuItems = menuView.menu.observeAsState(emptyList())

                Surface {
                    Navigation(
                        horizontalScreenPadding = horizontalScreenPadding,
                        context = applicationContext,
                        dishList = menuItems.value
                    )
                }

            }
        }
    }
}
