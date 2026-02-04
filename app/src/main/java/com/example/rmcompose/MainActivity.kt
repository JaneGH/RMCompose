package com.example.rmcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.rmcompose.presentation.AppNavGraph
import com.example.rmcompose.ui.theme.RMComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    val repository: RepositoryImpl by lazy{
//        RepositoryImpl(ApiService.getInstance())
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RMComposeTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}

