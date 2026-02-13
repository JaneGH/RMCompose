package com.example.rmcompose.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.serialization.json.Json
import coil.compose.rememberAsyncImagePainter


@Composable
fun MainScreen (onDetailedClick: (characterName: String) -> Unit,  viewModel: MainViewModel){

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val selectedText by viewModel.selectedText.collectAsStateWithLifecycle()

//    var imageUri by rememberSaveable() { mutableStateOf<Uri?>(null) }

    var imagesUrl by rememberSaveable() {mutableStateOf<List<Uri>>(emptyList()) }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) {
//        uri: Uri?-> imageUri = uri
//    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
        uris : List<Uri> -> imagesUrl = (imagesUrl + uris).distinct().take(10)
    }


    LaunchedEffect(Unit) {
        viewModel.getCharacters()
     }


    when(uiState){

        is MainScreenState.Idle -> {
            Text("Waiting for uploading...")
        }
        is MainScreenState.Success -> {
            Column(modifier = Modifier.systemBarsPadding()) {

                Button (onClick = {
                    launcher.launch("image/*")
                }) {
                    Text("Choose image")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                imageUri?.let {
//                    AsyncImage(
//                        model = it,
//                        contentDescription = "Selected image",
//                        modifier = Modifier
//                            .size(200.dp)
//                            .clip(RoundedCornerShape(16.dp)),
//                        contentScale = ContentScale.Crop
//                    )
//                }
                LazyHorizontalGrid (
                   rows = GridCells.Adaptive(300.dp)
                ) {
                    items(imagesUrl) {imagesUrl->
                        AsyncImage(
                            model = imagesUrl,
                            contentDescription = "picture"

                        )
                    }

                }


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
//                                val characterString = Uri.encode(Json.encodeToString(item))
                                 onDetailedClick(item.name)
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