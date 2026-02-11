package com.example.rmcompose.presentation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.json.Json


@Composable
fun MainScreen (onDetailedClick: (character: String) -> Unit,  viewModel: MainViewModel, selectedText: String){

    val uiState by viewModel.state.collectAsStateWithLifecycle()



    LaunchedEffect(Unit) {
        viewModel.getCharacters()
    }


    when(uiState){

        is MainScreenState.Idle -> {
            Text("Waiting for uploading...")
        }
        is MainScreenState.Success -> {
            Column(modifier = Modifier.systemBarsPadding()) {

                selectedText.let {
                    if (!selectedText.isEmpty()) {
                            Text("Returned text from details: $it")
                        }
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = "Names")
                }

                val listResults = (uiState as MainScreenState.Success).result
                LazyColumn(
                    modifier = Modifier.systemBarsPadding()
                ) {

                    items(listResults) { item ->
                        Card(
                            onClick = {
                                val characterString = Uri.encode(Json.encodeToString(item))
                                 onDetailedClick(characterString)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RectangleShape,
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp, bottom = 5.dp),
                                text = item.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }

        is MainScreenState.Error ->
            Text(text = "Error ${(uiState as MainScreenState.Error).message}")
        is MainScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                )
            }
        }

    }
}