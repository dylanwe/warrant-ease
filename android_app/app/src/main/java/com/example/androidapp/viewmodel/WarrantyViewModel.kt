package com.example.androidapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidapp.model.Example
import com.example.androidapp.repository.WarrantyRepository
import kotlinx.coroutines.launch

class WarrantyViewModel(application: Application) : AndroidViewModel(application) {
    private val warrantyRepository = WarrantyRepository()

    private val _example: MutableLiveData<Example?> = MutableLiveData(null)

    val example: LiveData<Example?>
        get() = _example

    fun getExample() {
        viewModelScope.launch {
            _example.value = warrantyRepository.getExample()
        }
    }
}