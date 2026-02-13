package com.example.rmcompose.services

import androidx.compose.runtime.staticCompositionLocalOf

val LocalMusicManager = staticCompositionLocalOf<MusicServiceManager> {
    error("MusicServiceManager not provided")
}
