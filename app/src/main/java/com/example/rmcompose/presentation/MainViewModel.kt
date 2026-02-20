package com.example.rmcompose.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor (private val getUserUseCase: GetUserUseCase,
                                          private val savedStateHandle: SavedStateHandle ) : ViewModel() {
    private var _state = MutableStateFlow<MainScreenState>(MainScreenState.Idle)
    val state: StateFlow<MainScreenState> = _state.asStateFlow()


    val selectedText: StateFlow<String> =
        savedStateHandle.getStateFlow("selectedText", "")

      @SuppressLint("SuspiciousIndentation")
      fun getCharacters() {
          _state.value = MainScreenState.Loading

              viewModelScope.launch() {
                  try {
                      _state.value =  MainScreenState.Success(getUserUseCase().results)
                  } catch (e: Exception) {
                      _state.value = MainScreenState.Error(message = e.message)
                  }
              }

      }



}