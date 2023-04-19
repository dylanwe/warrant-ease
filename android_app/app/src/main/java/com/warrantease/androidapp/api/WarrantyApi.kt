package com.warrantease.androidapp.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Connect to the Warranty back-end REST API
 *
 * @author Dylan Weijgertze
 */
class WarrantyApi {
    companion object {
        private const val WARRANTY_API_BASE = "http://10.0.2.2:8080/" // localhost on pc

        fun createApi(): com.warrantease.androidapp.api.WarrantyApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(com.warrantease.androidapp.api.AuthorizationInterceptor())
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }.build()

            val gson = GsonBuilder()
                .serializeNulls()
                .create()

            // Create the Retrofit instance
            return Retrofit.Builder()
                .baseUrl(com.warrantease.androidapp.api.WarrantyApi.Companion.WARRANTY_API_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(com.warrantease.androidapp.api.WarrantyApiService::class.java)
        }
    }
}