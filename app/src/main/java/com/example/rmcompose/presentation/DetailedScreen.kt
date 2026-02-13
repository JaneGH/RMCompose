package com.example.rmcompose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rmcompose.services.LocalMusicManager

@Composable
fun DetailedScreen(
    onBackWithResult: (textResult: String) -> Unit,
    characterName: String,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val musicManager = LocalMusicManager.current
    var textToSendBack by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = 10.dp, end = 10.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Detailed screen:"
            )
        }

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Name: $characterName"
        )

        OutlinedTextField(
            value = textToSendBack,
            onValueChange = { newText ->
                textToSendBack = newText
            },
            modifier = Modifier.padding(top = 20.dp),
            shape = RoundedCornerShape(16.dp),
            label = {
                Text("Enter text to send back")
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onBackWithResult(textToSendBack)
                }
            ) {
                Text("Send Back")
            }
        }


        Card(
            modifier = Modifier.padding(all = 20.dp)
        ) {
            Row(
                modifier = Modifier.padding(all = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        musicManager.play(
                            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
                        )
                    },
                    enabled = musicManager.isBound
                ) {
                    Text("Play Music")
                }

                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        musicManager.pause()
                    },
                    enabled = musicManager.isBound
                ) {
                    Text("Pause Music")
                }

                if (!musicManager.isBound) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Service not bound yet..."
                    )
                }
            }
        }
    }
}
