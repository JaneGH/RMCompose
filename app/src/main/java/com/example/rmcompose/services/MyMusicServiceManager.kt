package com.example.rmcompose.services

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class MyMusicServiceManager {
    private var service: MyService? = null
    private val connection = object : ServiceConnection{
        override fun onServiceConnected(
            name: ComponentName?,
            binder: IBinder?
        ) {
            val localBinder = binder as MyService.MyBinder
            service = localBinder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }

    }
}