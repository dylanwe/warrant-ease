package com.example.androidapp.api

import com.example.androidapp.model.Example
import retrofit2.http.GET
import retrofit2.http.Header

interface WarrantyApiService {
    @GET("/private")
    suspend fun getExample(): Example
}