package com.example.rmcompose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rmcompose.data.remote.dto.Result

@Composable
fun DetailedScreen( onBackWithResult:(textResult: String)->Unit, character : Result, viewModel: DetailViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier.systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.systemBarsPadding()
        ) {
            Text("Detailed")
            Text("Name: ${character.name}")

            val newData = "Hi from detail screen"

//            Button(
//                onClick = { onMainClick(newData) }
//            ) {
//                Text("Send data to main screen")
//            }

            Button(
                onClick = {
                    onBackWithResult("Send back data")
                }
            ) {
                Text("Send Back")
            }



        }



    }
}