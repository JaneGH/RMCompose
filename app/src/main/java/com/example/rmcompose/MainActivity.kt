package com.example.rmcompose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.rmcompose.presentation.AppNavGraph
import com.example.rmcompose.services.LocalMusicManager
import com.example.rmcompose.services.MusicServiceManager
import com.example.rmcompose.ui.theme.RMComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var musicManager: MusicServiceManager

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            startMusicService()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        musicManager = MusicServiceManager(this)

        requestNotificationPermission()

        setContent {
            RMComposeTheme {
                CompositionLocalProvider(
                    LocalMusicManager provides musicManager
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navController)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        musicManager.unbind()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val granted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        startMusicService()
    }

    private fun startMusicService() {
        musicManager.startAndBind()
    }
}
