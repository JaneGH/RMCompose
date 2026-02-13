package com.example.rmcompose.presentation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.rmcompose.data.remote.dto.Result
import kotlinx.serialization.json.Json

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {


        composable("home") {

            val viewModel: MainViewModel = hiltViewModel()

            val savedStateHandle =
                navController.currentBackStackEntry?.savedStateHandle


            MainScreen(
                viewModel = viewModel,
//                savedStateHandle = savedStateHandle,
                onDetailedClick = { characterName ->
                    navController.navigate("details/$characterName")
                }
            )
        }

        composable(
            route = "details/{characterName}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "rmcompose://details/{characterName}"
                }
            ),
            arguments = listOf(
                navArgument("characterName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

//            val encoded =
//                backStackEntry.arguments?.getString("character") ?: ""
//
//            val decoded = Uri.decode(encoded)
//
//            val character = Json.decodeFromString<Result>(decoded)

            val characterName = backStackEntry.arguments?.getString("characterName") ?: ""

            DetailedScreen(
                characterName = characterName,
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
