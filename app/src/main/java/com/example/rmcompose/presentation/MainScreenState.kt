package com.example.rmcompose.presentation

import com.example.rmcompose.data.remote.dto.Result


sealed class MainScreenState {
    object Idle: MainScreenState()
    data class Success (val result: List<Result>): MainScreenState()
    data class Error (val message: String?): MainScreenState()
    object Loading: MainScreenState()
}