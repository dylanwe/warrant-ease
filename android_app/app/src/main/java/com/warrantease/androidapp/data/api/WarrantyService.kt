package com.warrantease.androidapp.data.api

import com.warrantease.androidapp.data.api.models.WarrantyResponse
import com.warrantease.androidapp.data.api.network.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Factory

@Factory
class WarrantyService(private val httpClientProvider: HttpClientProvider) {
    suspend fun getAllWarranties(): List<WarrantyResponse> {
        return httpClientProvider.client.get("/api/v1/warranty").body()
    }
}
