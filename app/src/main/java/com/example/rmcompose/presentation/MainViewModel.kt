package com.example.rmcompose.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmcompose.domain.repository.IRepository
import com.example.rmcompose.domain.usercases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor (private val getUserUseCase: GetUserUseCase, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var _state = MutableStateFlow<MainScreenState>(MainScreenState.Idle)

    val selectedText: StateFlow<String?> =
        savedStateHandle.getStateFlow("selectedText", null)

    val state: StateFlow<MainScreenState> = _state.asStateFlow()
      @SuppressLint("SuspiciousIndentation")
      fun getCharacters() {
          _state.value = MainScreenState.Loading

              viewModelScope.launch(Dispatchers.IO) {
                  try {
                      _state.value =  MainScreenState.Success(getUserUseCase().results)
                  } catch (e: Exception) {
                      _state.value = MainScreenState.Error(message = e.message)
                  }
              }

      }


}