package com.example.rmcompose.domain.usercases
import com.example.rmcompose.data.remote.dto.CharactersResponse
import com.example.rmcompose.domain.repository.IRepository
import javax.inject.Inject

class GetUserUseCase  @Inject constructor (private val repository: IRepository) {
    suspend operator fun invoke() : CharactersResponse {
       return repository.getCharacters()
    }
}