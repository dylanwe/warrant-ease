package com.warrantease.androidapp.repository

import android.util.Log
import com.warrantease.androidapp.api.WarrantyApi
import com.warrantease.androidapp.api.WarrantyApiService
import com.warrantease.androidapp.model.Example
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

/**
 * Example repository
 */
class WarrantyRepository {
    private val warrantyApiService: WarrantyApiService = com.warrantease.androidapp.api.WarrantyApi.createApi()

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