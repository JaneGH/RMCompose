package com.example.rmcompose.data.remote.retrofit

import com.example.rmcompose.data.remote.dto.CharactersResponse
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    suspend fun getCharacters() : CharactersResponse

//    companion object {
//        fun getInstance():ApiService{
//            return ApiClient.retrofit.create(ApiService::class.java)
//        }
//    }
}