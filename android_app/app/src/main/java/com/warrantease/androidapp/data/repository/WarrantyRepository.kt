package com.warrantease.androidapp.data.repository

import android.util.Log
import com.warrantease.androidapp.data.api.WarrantyApi
import com.warrantease.androidapp.data.api.WarrantyApiService
import com.warrantease.androidapp.domain.model.Example
import kotlinx.coroutines.withTimeout
import org.koin.core.annotation.Single

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