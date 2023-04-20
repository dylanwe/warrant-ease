package com.warrantease.androidapp.repository

import android.util.Log
import com.warrantease.androidapp.api.WarrantyApi
import com.warrantease.androidapp.api.WarrantyApiService
import com.warrantease.androidapp.model.Example
import kotlinx.coroutines.withTimeout

/**
 * Example repository
 */
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