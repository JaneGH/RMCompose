package com.example.rmcompose.data.remote.repository

import com.example.rmcompose.data.remote.dto.CharactersResponse
import com.example.rmcompose.data.remote.retrofit.ApiService
import com.example.rmcompose.domain.repository.IRepository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val apiService: ApiService) : IRepository {
    override suspend fun getCharacters(): CharactersResponse {
        return apiService.getCharacters()
    }
}