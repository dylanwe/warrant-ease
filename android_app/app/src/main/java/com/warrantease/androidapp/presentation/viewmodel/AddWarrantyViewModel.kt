package com.warrantease.androidapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warrantease.androidapp.domain.WarrantyRepository
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddWarrantyViewModel(
	private val warrantyRepository: WarrantyRepository,
) : ViewModel() {

	private val _saveWarrantyState = MutableStateFlow(UIState.EMPTY)
	val saveWarrantyState: StateFlow<UIState> = _saveWarrantyState.asStateFlow()

	fun saveWarranty() {
		_saveWarrantyState.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			try {
				warrantyRepository.getAllWarranties()
				_saveWarrantyState.value = UIState.NORMAL
			} catch (exception: Exception) {
				Log.e("ERR", exception.stackTraceToString())
				_saveWarrantyState.value = UIState.ERROR
			}
		}
	}
}