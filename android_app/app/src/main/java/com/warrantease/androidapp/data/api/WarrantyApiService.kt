package com.warrantease.androidapp.data.api

import com.warrantease.androidapp.domain.model.Example
import retrofit2.http.GET

/**
 * Example of an API service
 *
 * @author Dylan Weijgertze
 */
interface WarrantyApiService {
    @GET("/private")
    suspend fun getExample(): Example
}