package com.example.rmcompose.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rmcompose.data.remote.dto.Result
import kotlinx.serialization.json.Json

@Composable
fun AppNavGraph (navController: NavHostController){

    NavHost(navController, startDestination = "home") {

        composable("home") {
            val viewModel: MainViewModel = hiltViewModel()

            MainScreen(
                viewModel = viewModel,
                onDetailedClick = { character ->
                    navController.navigate("details?character=$character")
                }
            )
        }



        composable( route = "details?character={character}", arguments = listOf(
            navArgument("character") {type = NavType.StringType}
        )) { backStackEntry ->

            val characterString = backStackEntry.arguments?.getString("character")
            val character = Json.decodeFromString<Result>(characterString ?: "")

            DetailedScreen(
                character = character,
                onBackWithResult = { selectedText ->

                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedText", selectedText)

                    navController.popBackStack()
                }
            )
        }
    }
}