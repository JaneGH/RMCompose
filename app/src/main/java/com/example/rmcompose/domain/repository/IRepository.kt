package com.example.rmcompose.domain.repository

import com.example.rmcompose.data.remote.dto.CharactersResponse

interface IRepository {
    suspend fun getCharacters() : CharactersResponse
}