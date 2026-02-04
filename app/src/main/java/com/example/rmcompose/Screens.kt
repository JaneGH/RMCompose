package com.example.rmcompose

sealed class Screens() {
    object Main: Screens()
    data class Detail(val userId: Int): Screens()
}