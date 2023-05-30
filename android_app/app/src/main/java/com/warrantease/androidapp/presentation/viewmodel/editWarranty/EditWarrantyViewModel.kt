package com.warrantease.androidapp.presentation.viewmodel.editWarranty

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warrantease.androidapp.domain.WarrantyRepository
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.viewmodel.editWarranty.model.EditWarrantyViewModelArgs
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class EditWarrantyViewModel(
	@InjectedParam private val args: EditWarrantyViewModelArgs,
	private val warrantyRepository: WarrantyRepository,
) : ViewModel() {

	private val _updateWarrantyState = MutableStateFlow(UIState.EMPTY)
	val updateWarrantyState: StateFlow<UIState> = _updateWarrantyState.asStateFlow()

	private val _uiState = MutableStateFlow(UIState.EMPTY)
	val uiState: StateFlow<UIState> = _uiState.asStateFlow()

	private val _warranty = MutableStateFlow<Warranty?>(null)
	val warranty: StateFlow<Warranty?> by lazy {
		getWarranty()
		_warranty.asStateFlow()
	}

	fun updateWarranty(updatedWarranty: Warranty) {
		_updateWarrantyState.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			try {
				warrantyRepository.updateWarranty(updatedWarranty)
				_updateWarrantyState.value = UIState.NORMAL
			} catch (exception: Exception) {
				Log.e("ERR", exception.stackTraceToString())
				_updateWarrantyState.value = UIState.ERROR
			}
		}
	}

	fun getWarranty() {
		_uiState.value = UIState.LOADING

		viewModelScope.launch(Dispatchers.IO) {
			try {
				_warranty.value = warrantyRepository.getWarrantyById(args.warrantyId)
				_uiState.value = UIState.NORMAL
			} catch (exception: Exception) {
				Log.e("ERR", exception.stackTraceToString())
				_uiState.value = UIState.ERROR
			}
		}
	}
}