package com.example.androidapp.repository

import android.util.Log
import com.example.androidapp.api.WarrantyApi
import com.example.androidapp.api.WarrantyApiService
import com.example.androidapp.model.Example
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

class WarrantyRepository {
    private val warrantyApiService: WarrantyApiService = WarrantyApi.createApi()

    suspend fun getExample(): Example? {
        var response: Example? = null
        try {
            withTimeout(5_000) {
                response = warrantyApiService.getExample()
            }
        } catch (e: Exception) {
            Log.e("WarrantyRepository", "Something went wrong ${e.message}")
        }

        return response
    }
}