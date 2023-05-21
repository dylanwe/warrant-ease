package com.warrantease.androidapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warrantease.androidapp.data.repository.WarrantyRepository
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class WarrantyViewModel : ViewModel() {
    private val warrantyRepository = WarrantyRepository()

    private val _state = MutableStateFlow(UIState.LOADING)
    val state: StateFlow<UIState> = _state.asStateFlow()

    private val _warranties = MutableStateFlow<List<Warranty>>(emptyList())
    val warranties: StateFlow<List<Warranty>> = _warranties.asStateFlow()

    fun getWarranties() {
        _state.value = UIState.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _warranties.value = warrantyRepository.getWarranties()
                if (warranties.value.isEmpty()) {
                    _state.value = UIState.EMPTY
                } else {
                    _state.value = UIState.NORMAL
                }
            } catch (exception: Exception) {
                Log.e("ERR", exception.stackTraceToString())
                _state.value = UIState.ERROR
            }
        }
    }
}