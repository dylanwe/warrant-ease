package com.warrantease.androidapp.data.api

import com.warrantease.androidapp.data.api.models.WarrantyResponse
import retrofit2.http.GET

/**
 * @author Dylan Weijgertze
 */
interface WarrantyApiService {
    @GET("/api/v1/warranty")
    suspend fun getWarranties(): List<WarrantyResponse>
}