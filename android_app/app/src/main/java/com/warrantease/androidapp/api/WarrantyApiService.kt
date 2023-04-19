package com.warrantease.androidapp.api

import com.warrantease.androidapp.model.Example
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